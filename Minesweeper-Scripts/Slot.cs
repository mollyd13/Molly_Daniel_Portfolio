using System.Collections;
using System.Collections.Generic;
using TMPro;
using Unity.VisualScripting;
using UnityEngine;
using UnityEngine.EventSystems;
using UnityEngine.UI;

public class Slot : MonoBehaviour, IPointerDownHandler, IPointerUpHandler
{
    //attributes
    public int value = 0;
    public bool bomb = false;
    public int row;
    public int column;
    //ui stuff
    public Sprite[] valueSprites;
    public TextMeshProUGUI labels;
    public Image cover;
    public Image flag;
    //access the minefield
    public GameObject minefield;
    public GameObject canvas;
    private GameManager gameManager;
    private MinefieldSetup minefieldSetup;
    //react to being touched/clicked
    public bool clicked = false;
    public bool heldDown = false;
    float holdTime = 0.5f;


    // Start is called before the first frame update
    void Start()
    {
        gameManager = canvas.GetComponent<GameManager>();
        minefieldSetup = minefield.GetComponent<MinefieldSetup>();
        StartCoroutine("startDelay");
    }

    IEnumerator startDelay()
    {
        yield return new WaitForSeconds(1);
        // labels.text = "Value: " + value + "\nRow: " + row + "\nColumn: " + column + "\nBomb: " + bomb;
        if (!bomb)
        {
            if (value > 0)
            {
                gameObject.GetComponent<Image>().sprite = valueSprites[value - 1];
            }
            else
            {
                gameObject.GetComponent<Image>().sprite = valueSprites[9];
            }
        }
        else
        {
            gameObject.GetComponent<Image>().sprite = valueSprites[8];
        }
    }

    public void checkValue()
    {
        if (!gameManager.isGameOver){
            if (bomb)
            {
                revealSlot();
                gameManager.gameOver();
            }
            else if (value == 0)
            {
                minefieldSetup.emptySpaceClicked(row, column);
            }
            else
            {
                revealSlot();
                clicked = true;
            }
        }
    }

    public void revealSlot()
    {
        gameObject.GetComponent<Button>().interactable = false;

        //animate the reveal and check if you won
        StartCoroutine(removeCoverDelay());
        LeanTween.rotate(cover.gameObject, new Vector3(0, 0, 25), .75f);
        LeanTween.move(cover.gameObject, new Vector3(transform.position.x + Random.Range(-75, 75), transform.position.y - Random.Range(0, 76), transform.position.z), .75f).setEase(LeanTweenType.easeOutExpo);
        LeanTween.scale(cover.gameObject, new Vector3(0, 0, 0), .75f);
    
    }

    public void OnPointerDown(PointerEventData data)
    {
        heldDown = true;
        if (flag.IsActive() && !gameManager.isGameOver){
            flag.gameObject.SetActive(false);
            gameManager.changeBombCountUI(2);
        }
        else{
            StartCoroutine(CheckIfHeld());
        }
    }

    public void OnPointerUp(PointerEventData eventData)
    {
        heldDown = false;
        if (!flag.IsActive())
        {
            checkValue();
        }
    }

    IEnumerator CheckIfHeld()
    {
        yield return new WaitForSeconds(holdTime);

        if (heldDown && !gameManager.isGameOver && !clicked)
        {
            flag.gameObject.SetActive(true);
            gameManager.changeBombCountUI(1);
        }
    }

    IEnumerator removeCoverDelay(){
        yield return new WaitForSeconds(.74f);
        cover.gameObject.SetActive(false);
        if (minefieldSetup.checkBoard())
        {
            gameManager.youWin();
        }
    }

}

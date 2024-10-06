using System;
using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class GameManager : MonoBehaviour
{
    private MinefieldSetup minefieldSetup;
    public TextMeshProUGUI bombCountUI;
    public TextMeshProUGUI timeUI;
    public GameObject gameOverScreen;
    public GameObject youWinScreen;
    public int time = 0;

    public bool isGameOver = false;

    // Start is called before the first frame update
    void Awake()
    {
        minefieldSetup = GameObject.Find("Minefield").GetComponent<MinefieldSetup>();
        bombCountUI.text = minefieldSetup.numBombs + "";
        InvokeRepeating("incrementTime", 1, 1);
    }

    public void changeBombCountUI(int direction){
        if (direction == 1){
            minefieldSetup.numBombs--;
            bombCountUI.text = minefieldSetup.numBombs + "";
        }
        else{
            minefieldSetup.numBombs++;
            bombCountUI.text = minefieldSetup.numBombs + "";
        }
    }

    public void incrementTime(){
        if (time < 999 && !isGameOver){
            time++;
            timeUI.text = time + "";
        }
    }

    public void gameOver(){
        isGameOver = true;
        LeanTween.scale(gameOverScreen, new Vector3(2, 2, 2), 2f).setEase(LeanTweenType.easeOutElastic);
    }

    public void playAgain(){
        isGameOver = false;
        SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex);
    }

    public void youWin(){
        LeanTween.scale(youWinScreen, new Vector3(1.5f, 1.5f, 1.5f), 2f).setEase(LeanTweenType.easeOutElastic);
        isGameOver = true;
    }
}

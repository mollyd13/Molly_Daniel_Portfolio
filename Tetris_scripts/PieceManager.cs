using System;
using System.Collections;
using System.Collections.Generic;
using Unity.VisualScripting;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.SocialPlatforms;

public class PieceManager : MonoBehaviour
{
    public Vector3 downVector;
    public Vector3 horVector;
    public float horInput;
    public float leftBound;
    public float rightBound;
    public float downDelay;
    public bool moveDownDelayOver;
    public bool rotateDelayDone;
    public bool scoreDelayDone;
    public bool rowNotFull;
    public int pieceIndex;
    public int nextPieceIndex;
    public GameObject currPiece;
    public GameObject[] piecesArr;
    private Piece pieceS;
    public Dictionary<Tuple<float, float>, GameObject> grid;
    private NextPiece next;
    private ScoreManager score;
    public GameObject nextManager;
    public GameObject scoreManager;
    public sceneManager sceneManager;

    void Awake()
    {
        next = nextManager.GetComponent<NextPiece>();
        score = scoreManager.GetComponent<ScoreManager>();
        sceneManager = GameObject.Find("SceneManager").GetComponent<sceneManager>();
        grid = new Dictionary<Tuple<float, float>, GameObject>();
        downDelay = .5f;
        moveDownDelayOver = true;
        rotateDelayDone = true;
        scoreDelayDone = true;
        downVector = new Vector3(0, .5f, 0);
        horVector = new Vector3(.5f, 0, 0);
        leftBound = -2.75f;
        rightBound = 2.75f;
        nextPieceIndex = UnityEngine.Random.Range(0, 7);
        spawnPiece();
    }
    void Update()
    {
        if (sceneManager.isRunning)
        {
            //move piece down
            if (pieceS.canMoveDown() && moveDownDelayOver)
            {
                currPiece.transform.localPosition -= downVector;
                moveDownDelayOver = false;
                StartCoroutine(moveDownDelay());
            }
            //handle horizontal input
            if (Input.GetKey(KeyCode.A) && pieceS.canMoveLeft() && pieceS.horDelayDone)
            {
                currPiece.transform.localPosition -= horVector;
                pieceS.horDelayDone = false;
                StartCoroutine(moveHorDelay());
            }
            else if (Input.GetKey(KeyCode.D) && pieceS.canMoveRight() && pieceS.horDelayDone)
            {
                currPiece.transform.localPosition += horVector;
                pieceS.horDelayDone = false;
                StartCoroutine(moveHorDelay());
            }
            //rotate piece if W is pressed
            if (Input.GetKey(KeyCode.W) && rotateDelayDone)
            {
                rotatePiece();
                rotateDelayDone = false;
                StartCoroutine(rotateDelay());
            }
            //move piece down faster if S is pressed
            if (Input.GetKey(KeyCode.S))
            {
                downDelay = .03f;
                if (scoreDelayDone){
                    score.score += 1;
                    scoreDelayDone = false;
                    StartCoroutine(scoreDelay());
                }
            }
            else
            {
                //down delay depends on level
                downDelay = .5f/score.level;
            }
            //if piece hits bottom, spawn a new one
            if (!pieceS.inBounds)
            {
                //if game is lost, stop game
                if (pieceS.hitTopBounds())
                {
                    sceneManager.isRunning = false;
                    sceneManager.GameOver();
                }
                //otherwise keep spawning pieces
                else
                {
                    spawnPiece();

                }
            }
        }
    }

    IEnumerator moveHorDelay()
    {
        yield return new WaitForSeconds(0.09f);
        pieceS.horDelayDone = true;

    }

    public IEnumerator moveDownDelay()
    {
        yield return new WaitForSeconds(downDelay);
        moveDownDelayOver = true;
    }

    IEnumerator rotateDelay()
    {
        yield return new WaitForSeconds(0.15f);
        rotateDelayDone = true;
    }

    IEnumerator scoreDelay(){
        yield return new WaitForSeconds(.1f);
        scoreDelayDone = true;
    }

    public void spawnPiece()
    {
        pieceIndex = nextPieceIndex;
        //instantiate new prefab, set its position, and make it the one being controlled
        GameObject pieceClone = Instantiate(piecesArr[pieceIndex], gameObject.transform) as GameObject;
        pieceClone.transform.localPosition = new Vector3(0, 5, 0);
        currPiece = pieceClone;
        pieceS = currPiece.GetComponent<Piece>();
        //determine what next piece will be
        nextPieceIndex = UnityEngine.Random.Range(0, 7);
        next.showNextPiece(nextPieceIndex);
    }

    public void updateGrid(Transform parentTransform)
    {
        //for every square in the tetronimo, add the square to the grid
        foreach (Transform child in parentTransform)
        {
            float roundedX = Mathf.Round(child.transform.position.x * 10.0f) * 0.1f;
            float roundedY = Mathf.Round(child.transform.position.y * 10.0f) * 0.1f;
            var key = new Tuple<float, float>(roundedX, roundedY);

            if (!grid.ContainsKey(key)) // Check if the key already exists
            {
                grid.Add(key, child.gameObject);
            }
            else
            {
                Debug.LogWarning($"Duplicate key: ({roundedX}, {roundedY})");
            }
        }
    }

    public void rotatePiece()
    {
        switch (pieceIndex)
        {
            //I tetronimo
            case 0:
                if (currPiece.transform.position.x >= -2f && currPiece.transform.position.x <= 2.5f)
                {
                    if (currPiece.transform.eulerAngles.z == 270)
                    {
                        currPiece.transform.Rotate(0, 0, 90);
                    }
                    else
                    {
                        currPiece.transform.Rotate(0, 0, -90);
                    }
                }
                break;
            //O tetronimo
            case 1:
                break;
            //J, L, T, S, and Z tetronimos
            default:
                if (currPiece.transform.position.x >= -2.5f && currPiece.transform.position.x <= 2.5f)
                {
                    currPiece.transform.Rotate(0, 0, 90, Space.World);
                }
                break;
        }
    }

    public void checkGrid(Transform parentTransform)
    {
        int rowsClearedCount = 0;
        //check every square in the tetronimo
        foreach (Transform child in parentTransform)
        {
            //assume row is false until proven otherwise
            rowNotFull = false;
            float roundedY = Mathf.Round(child.transform.position.y * 10.0f) * 0.1f;
            for (float x = -3; x <= 3f; x += .5f)
            {
                //if one square in this row is not filled, set flag for rowNotFull and go onto next child 
                if (!grid.ContainsKey(new Tuple<float, float>(x, roundedY)))
                {
                    rowNotFull = true;
                    break;
                }
            }
            //if row is still not full at the end of checking all squares in row, clear row and increment rowsCleared
            if (!rowNotFull)
            {
                clearRow(roundedY);
                rowsClearedCount += 1;
                score.lines += 1;
            }
        }
        // Debug.Log("rowsClearedCount: " + rowsClearedCount);
        //increase score according to amount of rows cleared by this piece
        if (rowsClearedCount == 1){
            // Debug.Log("increasing score by "+ (100 * score.level));
            score.score += 100 * score.level;
        }
        else if (rowsClearedCount == 2){
            // Debug.Log("increasing score by " + (300 * score.level));
            score.score += 300 * score.level;
        }
        else if (rowsClearedCount == 3){
            // Debug.Log("increasing score by " + (500 * score.level));
            score.score += 500 * score.level;
        }
        else if (rowsClearedCount == 4){
            // Debug.Log("increasing score by " + (800 * score.level));
            score.score += 800 * score.level;
        }
    }

    public void clearRow(float y)
    {
        // Collect keys to remove and update
        List<Tuple<float, float>> keysToRemove = new List<Tuple<float, float>>();
        List<Tuple<Tuple<float, float>, GameObject>> entriesToAdd = new List<Tuple<Tuple<float, float>, GameObject>>();

        //indicate all squares in row to be deleted from grid and delete actual gameobjects 
        for (float x = -3; x <= 3f; x += .5f)
        {
            var key = new Tuple<float, float>(x, y);
            Destroy(grid[key]);
            keysToRemove.Add(key);
        }

        //check for squares that were on top of removed row and move them down if possible
        foreach ((Tuple<float, float> key, GameObject value) in grid)
        {
            if (key.Item2 > y && key.Item2 >= -4)
            {
                value.transform.position -= downVector;
                keysToRemove.Add(key);
                //indicate squares that were moved down to be added to grid -- basically update original coordinates to be translated down .5
                var newKey = new Tuple<float, float>(key.Item1, key.Item2 - .5f);
                entriesToAdd.Add(new Tuple<Tuple<float, float>, GameObject>(newKey, value));
            }
        }

        // Remove the indicated keys from the dictionary
        foreach (var key in keysToRemove)
        {
            grid.Remove(key);
        }

        // Add the updated coordinates to the dictionary
        foreach (var entry in entriesToAdd)
        {
            grid.Add(entry.Item1, entry.Item2);
        }
    }

}

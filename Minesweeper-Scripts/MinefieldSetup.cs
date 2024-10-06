using System;
using System.Collections;
using System.Collections.Generic;
using TMPro;
using Unity.VisualScripting;
using UnityEngine;
using UnityEngine.AI;
using UnityEngine.SceneManagement;
using UnityEngine.UI;
using Random = UnityEngine.Random;

public class MinefieldSetup : MonoBehaviour
{
    [SerializeField] int rows;
    [SerializeField] int columns;
    public GameObject[] slotGOs;
    public Dictionary<Tuple<int, int>, GameObject> rowColToObjects;
    public int numBombs;

    public GameObject canvas;
    private GameManager gameManager;

    void Awake()
    {
        gameManager = canvas.GetComponent<GameManager>();
        //set bomb count text
        //initialize and fill slotGOs with all the child objects of MineField
        slotGOs = new GameObject[rows * columns];
        for (int i = 0; i < gameObject.transform.childCount; i++)
        {
            slotGOs[i] = gameObject.transform.GetChild(i).gameObject;
        }
        //initialize the dictionary 
        rowColToObjects = new Dictionary<Tuple<int, int>, GameObject>();


        // assign rows and columns
        assignRowsAndColumns();
        pickMines(numBombs);
        findValue();
    }

    public void assignRowsAndColumns()
    {

        int columnCount = 0;
        int rowCount = 0;
        for (int i = 0; i < slotGOs.Length; i++)
        {
            slotGOs[i].GetComponent<Slot>().row = rowCount;
            slotGOs[i].GetComponent<Slot>().column = columnCount;
            rowColToObjects.Add(new Tuple<int, int>(rowCount, columnCount), slotGOs[i]);
            if (columnCount == columns - 1)
            {
                columnCount = 0;
                rowCount += 1;
            }
            else
            {
                columnCount += 1;

            }
        }
    }

    public void pickMines(int numMines)
    {
        // assign a predetermined amount of mines to random coordinates
        for (int i = 0; i < numMines; i++)
        {
            int randomSlotIndex = Random.Range(0, slotGOs.Length);

            // if there is not already a bomb there put one
            if (slotGOs[randomSlotIndex].GetComponent<Slot>().bomb == false)
            {
                slotGOs[randomSlotIndex].GetComponent<Slot>().bomb = true;
                // slotGOs[randomSlotIndex].GetComponent<Image>().color = Color.red;
            }
            // else run it again
            else
            {
                i -= 1;
            }
        }
    }

    public void findValue()
    {
        for (int i = 0; i < slotGOs.Length; i++)
        {
            Tuple<int, int> rowAndCol = new Tuple<int, int>(slotGOs[i].GetComponent<Slot>().row, slotGOs[i].GetComponent<Slot>().column);
       
            //check each adjacent slot to see if there is a bomb
            if (rowColToObjects.ContainsKey(new Tuple<int, int>(rowAndCol.Item1-1, rowAndCol.Item2-1)) && rowColToObjects[new Tuple<int, int>(rowAndCol.Item1 - 1, rowAndCol.Item2 - 1)].GetComponent<Slot>().bomb)
            {
                slotGOs[i].GetComponent<Slot>().value += 1;
            }
            if (rowColToObjects.ContainsKey(new Tuple<int, int>(rowAndCol.Item1 - 1, rowAndCol.Item2)) && rowColToObjects[new Tuple<int, int>(rowAndCol.Item1 - 1, rowAndCol.Item2)].GetComponent<Slot>().bomb)
            {
                slotGOs[i].GetComponent<Slot>().value += 1;
            }
            if (rowColToObjects.ContainsKey(new Tuple<int, int>(rowAndCol.Item1 - 1, rowAndCol.Item2 + 1)) && rowColToObjects[new Tuple<int, int>(rowAndCol.Item1 - 1, rowAndCol.Item2 + 1)].GetComponent<Slot>().bomb)
            {
                slotGOs[i].GetComponent<Slot>().value += 1;
            }
            if (rowColToObjects.ContainsKey(new Tuple<int, int>(rowAndCol.Item1, rowAndCol.Item2 - 1)) && rowColToObjects[new Tuple<int, int>(rowAndCol.Item1, rowAndCol.Item2 - 1)].GetComponent<Slot>().bomb)
            {
                slotGOs[i].GetComponent<Slot>().value += 1;
            }
            if (rowColToObjects.ContainsKey(new Tuple<int, int>(rowAndCol.Item1, rowAndCol.Item2 + 1)) && rowColToObjects[new Tuple<int, int>(rowAndCol.Item1, rowAndCol.Item2 + 1)].GetComponent<Slot>().bomb)
            {
                slotGOs[i].GetComponent<Slot>().value += 1;
            }
            if (rowColToObjects.ContainsKey(new Tuple<int, int>(rowAndCol.Item1 + 1, rowAndCol.Item2 - 1)) && rowColToObjects[new Tuple<int, int>(rowAndCol.Item1 + 1, rowAndCol.Item2 - 1)].GetComponent<Slot>().bomb)
            {
                slotGOs[i].GetComponent<Slot>().value += 1;
            }
            if (rowColToObjects.ContainsKey(new Tuple<int, int>(rowAndCol.Item1 + 1, rowAndCol.Item2)) && rowColToObjects[new Tuple<int, int>(rowAndCol.Item1 + 1, rowAndCol.Item2)].GetComponent<Slot>().bomb)
            {
                slotGOs[i].GetComponent<Slot>().value += 1;
            }
            if (rowColToObjects.ContainsKey(new Tuple<int, int>(rowAndCol.Item1 + 1, rowAndCol.Item2 + 1)) && rowColToObjects[new Tuple<int, int>(rowAndCol.Item1 + 1, rowAndCol.Item2 + 1)].GetComponent<Slot>().bomb)
            {
                slotGOs[i].GetComponent<Slot>().value += 1;
            }
        }
    }

    public void emptySpaceClicked(int row, int column)
    {
        //remove cover at this spot
        rowColToObjects[new Tuple<int, int>(row, column)].GetComponent<Slot>().revealSlot();
        //base case to know when to stop calling the function
        if (rowColToObjects[new Tuple<int, int>(row, column)].GetComponent<Slot>().value != 0 || rowColToObjects[new Tuple<int, int>(row, column)].GetComponent<Slot>().clicked)
        {
            return;
        }
        //set as clicked so we don't get stuck in a loop
        rowColToObjects[new Tuple<int, int>(row, column)].GetComponent<Slot>().clicked = true;
        //top left
        if (rowColToObjects.ContainsKey(new Tuple<int, int>(row - 1, column - 1)))
        {
            emptySpaceClicked(row - 1, column - 1);
        }
        //top middle
        if (rowColToObjects.ContainsKey(new Tuple<int, int>(row - 1, column)))
        {
            emptySpaceClicked(row - 1, column);
        }
        //top right
        if (rowColToObjects.ContainsKey(new Tuple<int, int>(row - 1, column + 1)))
        {
            emptySpaceClicked(row - 1, column + 1);
        }
        //left
        if (rowColToObjects.ContainsKey(new Tuple<int, int>(row, column - 1)))
        {
            emptySpaceClicked(row, column - 1);
        }
        //right
        if (rowColToObjects.ContainsKey(new Tuple<int, int>(row, column + 1)))
        {
            emptySpaceClicked(row, column + 1);
        }
        //bottom left
        if (rowColToObjects.ContainsKey(new Tuple<int, int>(row + 1, column - 1)))
        {
            emptySpaceClicked(row + 1, column - 1);
        }
        //bottom mid
        if (rowColToObjects.ContainsKey(new Tuple<int, int>(row + 1, column)))
        {
            emptySpaceClicked(row + 1, column);
        }
        //bottom right
        if (rowColToObjects.ContainsKey(new Tuple<int, int>(row + 1, column + 1)))
        {
            emptySpaceClicked(row + 1, column + 1);
        }
        return;
    }
    public bool checkBoard(){
        for (int i=0; i<slotGOs.Length; i++){
            if (!slotGOs[i].GetComponent<Slot>().bomb){
                if (slotGOs[i].GetComponent<Slot>().cover.IsActive()){
                    return false;
                }
            }
        }
        return true;
    }
}

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
    public GameObject[,] rowColToObjects;
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
        rowColToObjects = new GameObject[rows,columns];

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
            rowColToObjects[rowCount, columnCount] = slotGOs[i];
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
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++){
                //check each adjacent slot to see if there is a bomb
                //bottom left
                if (i - 1 >= 0 && j - 1 >= 0 && rowColToObjects[i - 1, j - 1].GetComponent<Slot>().bomb)
                {
                    rowColToObjects[i,j].GetComponent<Slot>().value += 1;
                }
                //top middle
                if (i - 1 >= 0 && rowColToObjects[i - 1, j].GetComponent<Slot>().bomb)
                {
                    rowColToObjects[i,j].GetComponent<Slot>().value += 1;
                }
                //top right
                if (i - 1 >= 0 && j + 1 < columns && rowColToObjects[i - 1, j + 1].GetComponent<Slot>().bomb)
                {
                    rowColToObjects[i,j].GetComponent<Slot>().value += 1;
                }
                //middle left
                if (j - 1 >= 0 && rowColToObjects[i, j - 1].GetComponent<Slot>().bomb)
                {
                    rowColToObjects[i,j].GetComponent<Slot>().value += 1;
                }
                //middle right
                if (j + 1 < columns && rowColToObjects[i, j + 1].GetComponent<Slot>().bomb)
                {
                    rowColToObjects[i,j].GetComponent<Slot>().value += 1;
                }
                //bottom left
                if (i + 1 < rows && j - 1 >= 0 && rowColToObjects[i + 1, j - 1].GetComponent<Slot>().bomb)
                {
                    rowColToObjects[i,j].GetComponent<Slot>().value += 1;
                }
                //bottom middle
                if (i + 1 < rows && rowColToObjects[i + 1, j].GetComponent<Slot>().bomb)
                {
                    rowColToObjects[i,j].GetComponent<Slot>().value += 1;
                }
                //bottom right
                if (i + 1 < rows && j + 1 < columns && rowColToObjects[i + 1, j + 1].GetComponent<Slot>().bomb)
                {
                    rowColToObjects[i,j].GetComponent<Slot>().value += 1;
                }

            }
        }
    }

    public void emptySpaceClicked(int row, int column)
    {
        //remove cover at this spot
        rowColToObjects[row, column].GetComponent<Slot>().revealSlot();
        //base case to know when to stop calling the function
        if (rowColToObjects[row, column].GetComponent<Slot>().value != 0 || rowColToObjects[row, column].GetComponent<Slot>().clicked)
        {
            return;
        }
        //set as clicked so we don't get stuck in a loop
        rowColToObjects[row, column].GetComponent<Slot>().clicked = true;
        //top left
        if (row - 1 >= 0 && column - 1 >= 0)
        {
            emptySpaceClicked(row - 1, column - 1);
        }
        //top middle
        if (row - 1 >= 0)
        {
            emptySpaceClicked(row - 1, column);
        }
        //top right
        if (row - 1 >= 0 && column + 1 < columns)
        {
            emptySpaceClicked(row - 1, column + 1);
        }
        //left
        if (column - 1 >= 0)
        {
            emptySpaceClicked(row, column - 1);
        }
        //right
        if (column + 1 < columns)
        {
            emptySpaceClicked(row, column + 1);
        }
        //bottom left
        if (row + 1 < rows && column - 1 >= 0)
        {
            emptySpaceClicked(row + 1, column - 1);
        }
        //bottom mid
        if (row + 1 < rows)
        {
            emptySpaceClicked(row + 1, column);
        }
        //bottom right
        if (row + 1 < rows && column + 1 < columns)
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

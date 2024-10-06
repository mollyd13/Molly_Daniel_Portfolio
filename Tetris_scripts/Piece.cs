using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System;

public class Piece : MonoBehaviour
{
    public bool inBounds;
    public bool horDelayDone;
    public PieceManager pieceManager;
    public float roundedX;
    public float roundedY;
    public bool isLanding;
    void Awake()
    {
        inBounds = true;
        isLanding = false;
        pieceManager = GetComponentInParent<PieceManager>();
        horDelayDone = true;
    }
    public bool canMoveLeft()
    {
        foreach (Transform child in transform)
        {
            roundedX = Mathf.Round(child.transform.position.x * 10.0f) * 0.1f;
            roundedY = Mathf.Round(child.transform.position.y * 10.0f) * 0.1f;
            //if on the edge of the grid or the square to the left is already taken, return false
            if (child.transform.position.x < -2.8f || pieceManager.grid.ContainsKey(new Tuple<float, float>(roundedX - .5f, roundedY)))
            {
                return false;
            }
        }
        return true;
    }

    public bool canMoveRight()
    {
        foreach (Transform child in transform)
        {
            roundedX = Mathf.Round(child.transform.position.x * 10.0f) * 0.1f;
            roundedY = Mathf.Round(child.transform.position.y * 10.0f) * 0.1f;
            //if on the edge of the grid or the square to the right is already taken, return false
            if (child.transform.position.x > 2.8f || pieceManager.grid.ContainsKey(new Tuple<float, float>(roundedX + .5f, roundedY)))
            {
                return false;
            }
        }
        return true;
    }

    public bool canMoveDown()
    {
        foreach (Transform child in transform)
        {
            roundedX = Mathf.Round(child.transform.position.x * 10.0f) * 0.1f;
            roundedY = Mathf.Round(child.transform.position.y * 10.0f) * 0.1f;

            //if on the edge of the grid or the square below is already taken, allow the player to move piece horizontally for grace period and then return false
            if (child.transform.position.y <= -4 || pieceManager.grid.ContainsKey(new Tuple<float, float>(roundedX, roundedY - .5f)))
            {
                if (!isLanding)
                {
                    StartCoroutine(gracePeriod());
                }
                return false;
            }
        }
        return true;
    }

    public bool hitTopBounds(){
        foreach (Transform child in transform)
        {
            roundedX = Mathf.Round(child.transform.position.x * 10.0f) * 0.1f;
            roundedY = Mathf.Round(child.transform.position.y * 10.0f) * 0.1f;

            //if any of the squares in the tetronimo are hitting the top, return true
            if (roundedY >= 4.5)
            {
                Debug.Log("hit top bounds");
                return true;
            }
        }
        return false;
    }

    IEnumerator gracePeriod()
    {
        isLanding = true;

        // give the player a grace period
        yield return new WaitForSeconds(0.3f);

        if (Input.GetAxis("Horizontal") != 0)
        {
            // if horizontal input is detected during the grace period, start the extended movement period
            yield return StartCoroutine(extendedMovementPeriod());
        }

        // after the grace period and (if applicable) the extended movement period, lock the piece
        pieceManager.updateGrid(transform);
        pieceManager.checkGrid(transform);
        inBounds = false;
        isLanding = false;
    }

    //allow the player to move for .75 more seconds
    IEnumerator extendedMovementPeriod()
    {
        float timeElapsed = 0f;
        float extendedMovementDuration = .75f;

        while (timeElapsed < extendedMovementDuration || canMoveDown())
        {
            // Allow time to continue
            timeElapsed += Time.deltaTime;
            yield return null;
        }
    }
}

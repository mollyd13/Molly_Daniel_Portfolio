using System.Collections;
using System.Collections.Generic;
using Unity.VisualScripting;
using UnityEngine;

public class NextPiece : MonoBehaviour
{
    public GameObject z;
    public GameObject t;
    public GameObject o;
    public GameObject i;
    public GameObject j;
    public GameObject l;
    public GameObject s;
    public GameObject prev;


    //sets the current piece shown to be inactive and shows the next piece
    public void showNextPiece(int pieceIndex)
    {
        switch (pieceIndex)
        {
            case 0:
                prev.SetActive(false);
                i.SetActive(true);
                prev = i;
                break;
            case 1:
                prev.SetActive(false);
                o.SetActive(true);
                prev = o;
                break;
            case 2:
                prev.SetActive(false);
                t.SetActive(true);
                prev = t;
                break;
            case 3:
                prev.SetActive(false);
                j.SetActive(true);
                prev = j;
                break;
            case 4:
                prev.SetActive(false);
                l.SetActive(true);
                prev = l;
                break;
            case 5:
                prev.SetActive(false);
                s.SetActive(true);
                prev = s;
                break;
            case 6:
                prev.SetActive(false);
                z.SetActive(true);
                prev = z;
                break;
        }
    }

}

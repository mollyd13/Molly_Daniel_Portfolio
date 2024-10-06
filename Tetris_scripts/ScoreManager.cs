using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;

public class ScoreManager : MonoBehaviour
{
    public GameObject scoreTxt;
    public GameObject levelTxt;
    public GameObject linesTxt;
    public int score;
    public int level;
    public int lines;
    // Start is called before the first frame update
    void Start()
    {
        score = 0;
        level = 1;
        lines = 0;
    }

    // Update is called once per frame
    void Update()
    {
        //if we hit an increment of 10 lines -- 10, 20, 30, etc -- increase the level
        if (lines > 0 && lines % 10 == 0){
            level = lines/10 + 1;
        }
        scoreTxt.GetComponent<TextMeshProUGUI>().text = "Score: " + score;
        levelTxt.GetComponent<TextMeshProUGUI>().text = "Level: " + level;
        linesTxt.GetComponent<TextMeshProUGUI>().text = "Lines: " + lines;
    }
}

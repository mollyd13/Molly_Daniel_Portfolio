using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class sceneManager : MonoBehaviour
{

    public void startMedium()
    {
        SceneManager.LoadScene("Medium");
    }

    public void openMenu()
    {
        SceneManager.LoadScene("Menu");
    }

    public void startEasy()
    {
        SceneManager.LoadScene("Easy");
    }

    public void startHard()
    {
        SceneManager.LoadScene("Hard");
    }

    public void Quit()
    {
        Application.Quit();
    }
}

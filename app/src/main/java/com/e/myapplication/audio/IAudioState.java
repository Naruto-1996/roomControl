package com.e.myapplication.audio;

public abstract class IAudioState {
  public IAudioState() {
  }

  void enter() {
  }

  abstract void handleMessage(AudioStateMessage var1);
}
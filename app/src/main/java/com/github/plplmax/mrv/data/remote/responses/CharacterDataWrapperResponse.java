package com.github.plplmax.mrv.data.remote.responses;

public class CharacterDataWrapperResponse {
    public int code;
    public String status;
    public CharacterDataContainerResponse data;

    public CharacterDataWrapperResponse(int code, String status, CharacterDataContainerResponse data) {
        this.code = code;
        this.status = status;
        this.data = data;
    }
}
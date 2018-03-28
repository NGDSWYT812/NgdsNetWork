package cn.com.ngds.gamestore.api.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import cn.com.ngds.lib.network.type.BaseType;

/**
 * Created by ytzyg on 15/1/7.
 */
public class ChannelGuide extends BaseType {
    @SerializedName("channel_id")
    private String channelId;

    @SerializedName("channel_name")
    private String channelName;

    @SerializedName("gamepad_brand")
    private String gamepadBrand;

    private List<GamePad> gamepads;

    @SerializedName("is_gamepad_guide")
    private int isGameGuide; //0:关闭 其他:打开

    @SerializedName("is_oauth_guide")
    private int isOauthGuide; //0:关闭 其他:打开

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getGamepadBrand() {
        return gamepadBrand;
    }

    public void setGamepadBrand(String gamepadBrand) {
        this.gamepadBrand = gamepadBrand;
    }

    public List<GamePad> getGamepads() {
        return gamepads;
    }

    public void setGamepads(List<GamePad> gamepads) {
        this.gamepads = gamepads;
    }

    public int getIsGameGuide() {
        return isGameGuide;
    }

    public void setIsGameGuide(int isGameGuide) {
        this.isGameGuide = isGameGuide;
    }

    public boolean isGameGuide() {
        if (isGameGuide == 0) {
            return false;
        }
        return true;
    }

    public int getIsOauthGuide() {
        return isOauthGuide;
    }

    public void setIsOauthGuide(int isOauthGuide) {
        this.isOauthGuide = isOauthGuide;
    }

    public boolean isOauthGuide() {
        if (isOauthGuide == 0) {
            return false;
        }
        return true;
    }
}

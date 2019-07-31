package com.tys.entity.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @Author haoxu
 * @Date 2019/4/15 12:00
 **/
public class DetectRecordVo implements Serializable {

    List<DrComplexSetting> drComplexSettings;

    List<DrProblemSetting> drProblemSettings;

    List<FaceValue> faceValues;

    public List<DrComplexSetting> getDrComplexSettings() {
        return drComplexSettings;
    }

    public void setDrComplexSettings(List<DrComplexSetting> drComplexSettings) {
        this.drComplexSettings = drComplexSettings;
    }

    public List<DrProblemSetting> getDrProblemSettings() {
        return drProblemSettings;
    }

    public void setDrProblemSettings(List<DrProblemSetting> drProblemSettings) {
        this.drProblemSettings = drProblemSettings;
    }

    public List<FaceValue> getFaceValues() {
        return faceValues;
    }

    public void setFaceValues(List<FaceValue> faceValues) {
        this.faceValues = faceValues;
    }
}

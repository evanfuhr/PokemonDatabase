package com.evanfuhr.pokemondatabase.models;

import java.util.ArrayList;
import java.util.List;

public class Move implements Comparable<Move> {

    private int mAccuracy = 0;
    private DamageClass mCategory = DamageClass.STATUS;
    private String mEffect;
    private int id = 0;
    private int mLevel = 0;
    private MoveMethod mMethodId = MoveMethod.LEVEL_UP;
    private String mName;
    private int mPower = 0;
    private int mPp = 0;
    private int mTm = 0;
    private Type mType;
    private int mCritRate = 0;

    // This one is hacky since there can "technically" be multiple different effect chances, but
    // after some data research, all instances of multiple non-zero values are still equal and thus,
    // can be consolidated.
    private int mEffectChance = 0;

    public int getAccuracy() {
        return this.mAccuracy;
    }

    public void setAccuracy(int accuracy) {
        this.mAccuracy = accuracy;
    }
    
    public DamageClass getCategory() {
        return this.mCategory;
    }

    public void setCategory(DamageClass category) {
        this.mCategory = category;
    }

    public String getEffect() {
        return this.mEffect;
    }

    public void setEffect(String effect) {
        this.mEffect = effect;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return this.mLevel;
    }

    public void setLevel(int level) {
        this.mLevel = level;
    }

    public MoveMethod getMethodID() {
        return this.mMethodId;
    }

    public void setMethodID(MoveMethod method_id) {
        this.mMethodId = method_id;
    }

    public String getName() {
        return this.mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public int getPower() {
        return this.mPower;
    }

    public void setPower(int power) {
        this.mPower = power;
    }

    public int getPP() {
        return this.mPp;
    }

    public void setPP(int pp) {
        this.mPp = pp;
    }

    public int getTM() {
        return mTm;
    }

    public void setTM(int tm) {
        this.mTm = tm;
    }

    public Type getType() {
        return this.mType;
    }

    public void setType(Type type) {
        this.mType = type;
    }

    //Static methods

    @Override
    public int compareTo(Move move) {
        if (this.getLevel() == ((Move) move).getLevel())
            return 0;
        else if ((this.getLevel()) > ((Move) move).getLevel())
            return 1;
        else
            return -1;
    }

    public int getCritRate() {
        return mCritRate;
    }

    public void setCritRate(int critRate) {
        this.mCritRate = critRate;
    }

    public int getEffectChance() {
        return mEffectChance;
    }

    public void setEffectChance(int effectChance) {
        this.mEffectChance = effectChance;
    }
}

package edu.seu.exceptions;

import edu.seu.base.CodeEnum;

public class COIPPIEExceptions extends Exception{
    CodeEnum codeEnum;

    public CodeEnum getCodeEnum(){return codeEnum;}

    public void setCodeEnum(CodeEnum codeEnum){
        this.codeEnum = codeEnum;
    }
    public COIPPIEExceptions(){
    }
    public COIPPIEExceptions(CodeEnum codeEnum, String msg)
    {
        super(msg);
        this.codeEnum = codeEnum;
    }
    public COIPPIEExceptions(String msg)
    {
        super(msg);
    }

}

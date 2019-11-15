package edu.seu.exceptions;

import edu.seu.base.CodeEnum;

public class OICPMPIEExceptions extends Exception{
    CodeEnum codeEnum;

    public CodeEnum getCodeEnum(){return codeEnum;}

    public void setCodeEnum(CodeEnum codeEnum){
        this.codeEnum = codeEnum;
    }
    public OICPMPIEExceptions(){
    }
    public OICPMPIEExceptions(CodeEnum codeEnum, String msg)
    {
        super(msg);
        this.codeEnum = codeEnum;
    }
    public OICPMPIEExceptions(String msg)
    {
        super(msg);
    }

}

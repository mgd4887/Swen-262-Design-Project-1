package Request;

public class Problem {
    String type;
    String message;

    public Problem(String _type, String _message){
        type = _type;
        message = _message;
    }

    public String getType(){
        return type;
    }

    public String getMessage(){
        return message;
    }

    public String toString(){
        return getType();
    }
}
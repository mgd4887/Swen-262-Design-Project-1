package Request;

import java.util.ArrayList;
import java.util.List;

public class RegisterVisitor implements Request {

    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;

    public RegisterVisitor(String _firstName, String _lastName, String _address, String _phoneNumber){
        firstName = _firstName;
        lastName = _lastName;
        address = _address;
        phoneNumber = _phoneNumber;
    }
//
    @Override
    public List<Object> ExecuteCommand(){
        List<Object> output = new ArrayList<Object>();
        return output;
    }

    @Override
    public List<Object> UndoCommand(){
        List<Object> output = new ArrayList<>();
        output.add(new Problem("cannot-undo", "The most recently executed command cannot be undone."));
        return output;
    }
}
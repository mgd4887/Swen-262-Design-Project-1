package Response;


import java.util.ArrayList;
import java.util.List;

public class BookSearch implements Response {

    public String perform(List<String> params) {

        String clientId = params.remove(0);

        List<Object> input = new ArrayList<>();

        if(params.size() < 1){
            return String.format("%s,search,missing-parameters,title;",clientId);
        }

        ArrayList<String> par = new ArrayList<>();

        for(String s : params){
            par.add(s);
        }

        String title = par.get(0);
        par.remove(0);

        for(String s : par){
            try{
                Long x = Long.parseLong(s);
                input.add(x);
            } catch(NumberFormatException e){
                input.add(s);
            }
        }

        return null;
    }
}
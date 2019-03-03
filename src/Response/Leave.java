package Response;

import Request.EndVisit;
import Request.Problem;


import java.time.Duration;
import java.time.LocalTime;
import java.util.List;


public class Leave implements Response{

    public String perform(List<String> params) {

        String clientId = params.remove(0);

        EndVisit ev = null;

        List<Object> results = null;
        if(params.isEmpty()) {
            return null;

        } else {
            ev = new EndVisit(clientId, params.get(0));
            results = ev.ExecuteCommand();

        }

        if(results.get(0) instanceof Problem){
            return String.format("%s,depart,%s;",clientId,results.get(0));
        }

        LocalTime endTime = (LocalTime) results.get(1);

        Duration length = (Duration) results.get(2);

        long hours = length.toHours();
        length.minusHours(hours);

        long minutes = length.toMinutes();
        length.minusMinutes(minutes);

        long seconds = length.getSeconds();

        String visitorId = (String) results.get(0);

//        LBMS.ur.pushToDone(clientId, ev);

        String output = String.format("%s,depart,%s,%02d:%02d:%02d,%02d:%02d:%02d;",clientId,visitorId,endTime.getHour(),
                endTime.getMinute(),endTime.getSecond(),hours,minutes,seconds);

        return output;
    }
}
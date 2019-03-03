package Response;

import Request.CurrentDateTime;
import Request.Request;
import java.time.LocalDateTime;
import java.util.List;

public class DateTime implements Response {

    public String perform(List<String> params){

        String clientId = params.remove(0);
        Request cdat = new CurrentDateTime();
        LocalDateTime dt = (LocalDateTime) cdat.ExecuteCommand().get(0);
        return String.format("%s,datetime,%02d/%02d/%d,%02d:%02d:%02d;",clientId,dt.getDayOfMonth(),dt.getMonthValue(),dt.getYear(),
                dt.getHour(),dt.getMinute(),dt.getSecond());
    }
}

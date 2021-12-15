package mac.sanju.codesharingplatform.layers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class CodeService {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    CodeRepository codeRepository;

    public String getNewCodeForm() {
        return "create";
    }

    public Object postNewApiCode(Code code) {
        String uuid = UUID.randomUUID().toString();
        code.setUuid(uuid);
        code.setDate(LocalDateTime.now().format(dateTimeFormatter));
        codeRepository.save(code);
        return Map.of("id", uuid);
    }

    public Object getCodeById(String id) throws ParseException {
        Optional<Code> code =  codeRepository.findById(id);
        if (!code.isPresent()) {
            return HttpStatus.NOT_FOUND;
        } else {
            if (checkCodeAvailability(code.get())) {
                updateData(code.get());
                return code.get();
            } else {
                return HttpStatus.NOT_FOUND;
            }
        }
    }

    protected void updateData(Code code) throws ParseException {
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
//        System.out.println(t1.toLocalTime());

        LocalDateTime codeDate = LocalDateTime.parse(code.getDate(), dateTimeFormatter);
//        System.out.println(t2.toLocalTime());

        Date d1 = simpleDateFormat.parse(now.toLocalTime().toString());
        Date d2 = simpleDateFormat.parse(codeDate.toLocalTime().toString());
        long diffInMs = d1.getTime()-d2.getTime();
        System.out.println((diffInMs/1000)%60);
        int newTime = (int) ((diffInMs/1000)%60);
        code.setTime(code.getTime() - newTime);
        code.setViews(code.getViews()-1);
        codeRepository.save(code);
    }

    private boolean checkCodeAvailability(Code code) {
        if (code.getViews() <= 0 || code.getTime() <= 0) {
            return false;
        } return true;
    }

}

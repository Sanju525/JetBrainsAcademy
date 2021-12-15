package platform.codeshare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import platform.exceptions.NotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CodeShareService {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private CodeShareRepository codeShareRepository;

    public String getForm() {
        return "create";
    } // -> post to below function.

    public Object postNewCode(Code code) {
        String uuid = UUID.randomUUID().toString();
        while (codeShareRepository.findById(uuid).isPresent()) {
            uuid = UUID.randomUUID().toString();
        }
        code.setUuid(uuid);
        code.setDate(LocalDateTime.now().format(dateTimeFormatter));
        codeShareRepository.save(code);
        return Map.of("id", uuid);
    }

    public Optional<Code> getApiCodeById(String uuid) {
        Optional<Code> c = codeShareRepository.findById(uuid);
        if (c.isEmpty()) {
            throw new NotFoundException();
        }
        return c;
    }

    public List<Code> getLatestApiCode() {
        int i=0;
        List<Code> latestCodes = new ArrayList<>();// sublist
        List<Code> getAll = codeShareRepository.findAll();
        for (Code code : getAll) {
            if (i<10) {
                if (code.getViews().equals("0") && code.getTime().equals("0")) {
                    latestCodes.add(code);
                    i++;
                }
            } else {
                break;
            }
        }
        return latestCodes;// sublist
    }

    public String getCodeById(String uuid, Model model) {
        model.addAttribute("title", "Code");
        model.addAttribute("codeById", getApiCodeById(uuid).get());
        return "code";
    }

    public String getLatestCode(Model model) {
        model.addAttribute("title", "Latest");
        model.addAttribute("codeList", getLatestApiCode());
        return "code";
    }


}
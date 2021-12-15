package mac.sanju.codesharingplatform.layers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@Controller
public class CodeController {

    @Autowired
    CodeService codeService;

    @GetMapping("/code/new")
    public String getNewCode() {
        return codeService.getNewCodeForm();
    }

    @RequestMapping(value = "/api/code/new", method = RequestMethod.POST)
    @ResponseBody
    public Object postNewApiCode(@RequestBody Code code) {
        return codeService.postNewApiCode(code);
    }

    @RequestMapping("api/code/{uuid}")
    @ResponseBody
    public Object getCodeById(@PathVariable String uuid) throws ParseException {
        return codeService.getCodeById(uuid);
    }

}

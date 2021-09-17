package cinema.theatre;

import cinema.exceptions.BadRequestException;
import cinema.exceptions.UnauthorizedErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
public class SeatController {
    @Autowired
    private SeatService seatService;

    @RequestMapping(method = RequestMethod.GET, value = "seats")
    public Map<String, Object> getAvailableSeats() {
        return seatService.getAvailableSeats();
    }

    @RequestMapping(method = RequestMethod.POST, value = "purchase")
    public Object purchaseSeat(@RequestBody Seat seat) {
        return seatService.addSeat(seat);
    }


    @RequestMapping("filledseats")
    public List<Seat> filledSeats() {
        return seatService.getFilledSeats();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/return")
    public Map<String, Object> cancelTicket(@RequestBody Map<String, String> token) {
        System.out.println("token" + token.get("token"));
        return seatService.cancelTicket(token.get("token"));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/stats")
    public Object statistics(@RequestParam(value = "password", required = false) String password) {
        System.out.println(password);
        return seatService.getStats(password);
    }

//    @RequestMapping(method = RequestMethod.POST, value = "/stats")
//    public Object emptyPassword() {
//        throw new UnauthorizedErrorException("The password is wrong!");
////        return Map.of("error", "The password is wrong!");
//    }
}

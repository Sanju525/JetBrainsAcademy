package cinema.theatre;

import cinema.exceptions.BadRequestException;
import cinema.exceptions.UnauthorizedErrorException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class SeatService {
    List<Seat> filledSeats = new ArrayList<>();
    Map<String, Seat> seatTokens = new HashMap<>();
    private int income=0;
    private int tickets_purchased=0;
    private int availableSeats=81;

    private List<Seat> initSeats(){
        int price=10;
        List<Seat> availableSeats = new ArrayList<>();
        for (int i=1;i<=9;i++) {
            if (i>4) {
                price=8;
            }
            for (int j=1;j<=9;j++) {
                Seat temp = new Seat(i,j,price);
                if (!filledSeats.contains(temp)) {
                    availableSeats.add(temp);
                }
            }
        }
        return availableSeats;
    }

    private void cancelSeat(Seat seat) {
        int i=0;
        for(Seat seat1: filledSeats) {
            if (seat1.equals(seat)) {
                filledSeats.remove(i);
                break;
            }
            i++;
        }
    }

    public Map<String, Object> getAvailableSeats() {
        List<Seat> availableSeats = initSeats();
        return Map.of("total_rows", 9,
                "total_columns", 9,
                "available_seats", availableSeats);
    }

    public List<Seat> getFilledSeats() {
        return filledSeats;
    }

    public Map<String, Object> addSeat(Seat seat) {
        boolean exits=false;
        String tokenId;
        Map<String, Seat> retMap;
        if (seat.getRow() > 9 || seat.getColumn()>9 || seat.getColumn()<1 || seat.getRow()<1) {
            // thor Exception BAD_REQUEST
            // The number of a row or a column is out of bounds!
            throw new BadRequestException("The number of a row or a column is out of bounds!");
        }
        for (Seat seat1: filledSeats) {
            if(seat1.getRow() == seat.getRow() && seat1.getColumn() == seat.getColumn()) {
                exits = true;
            }
        }
        if (exits) {
            // throw Exception BAD_REQUEST
            // The ticket has been already purchased!
            throw new BadRequestException("The ticket has been already purchased!");
        } else {
            if (seat.getRow() > 4) {
                seat.setPrice(8);
            } else {
                seat.setPrice(10);
            }
            filledSeats.add(seat);
            availableSeats-=1;
            tickets_purchased+=1;
            income+=seat.getPrice();
            tokenId = UUID.randomUUID().toString();
            seatTokens.put(tokenId, seat);
        }
        return Map.of("token", tokenId, "ticket", seat);
    }

    public Map<String, Object> cancelTicket(String token) {
        Seat cancelledSeat;
        System.out.println(seatTokens);
        if (seatTokens.containsKey(token)) {
            cancelSeat(seatTokens.get(token));
            cancelledSeat = seatTokens.remove(token);
            availableSeats+=1;
            income-=cancelledSeat.getPrice();
            tickets_purchased-=1;

        } else {
            throw new BadRequestException("Wrong token!");
        }
        return Map.of("returned_ticket", cancelledSeat);
    }

    public Object getStats(String password) {
        if (password==null) {
            throw new UnauthorizedErrorException("The password is wrong!");
        }
        if (password.equals("super_secret")) {
            return Map.of("current_income", income,
                    "number_of_available_seats", availableSeats,
                    "number_of_purchased_tickets", tickets_purchased);
        }else {
            throw new UnauthorizedErrorException("The password is wrong!");
        }
    }






    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getTickets_purchased() {
        return tickets_purchased;
    }

    public void setTickets_purchased(int tickets_purchased) {
        this.tickets_purchased = tickets_purchased;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

}

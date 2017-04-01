package bridge_data_structures;

public class Contract {
	int number;
	Suit suit;		//null indicates no trumps
	int declarerPosition;
	
	public Contract(int number, Suit suit, int declarerPosition) {
		this.number = number;
		this.suit = suit;
		this.declarerPosition = declarerPosition;
	}
	
	public Contract(String[] parser_tokens){
		number = (int)parser_tokens[0].charAt(0);
		suit = Card.convertToSuit(parser_tokens[0].charAt(1));
		declarerPosition = convertToPosition(parser_tokens[1].charAt(0));
	}
	
	public String toString(){
		String suit_string;
		if (suit == null) suit_string = "NT";
		else suit_string = suit.name();
		return number + " " + suit_string + " from " + Position.getName(declarerPosition);
	}
	
	public boolean isGreaterThan(Contract contract){
		if (contract == null) return true;
		
		if (suit == contract.getSuit())
			return (number > contract.getNumber());
		
		if (this.hasGreaterSuitThan(contract)){
			return (number >= contract.getNumber());
		}
		else 
			return (number > contract.getNumber());
	}
	
	private boolean hasGreaterSuitThan(Contract contract){
		//Input contract will never have the same suit
		
		if (contract.getSuit() == null) return false;
		
		switch(suit){
			case CLUB: if (contract.getSuit() == Suit.DIAMOND) return false;
			case DIAMOND: if (contract.getSuit() == Suit.HEART) return false;
			case HEART: if (contract.getSuit() == Suit.SPADE) return false;
			default: return true;
		}
	}
	
	public int convertToPosition(char c){
		switch(c){
		case 'N': return Position.NORTH;
		case 'S': return Position.SOUTH;
		case 'W': return Position.WEST;
		case 'E': return Position.EAST;
		default: return -1;
		}
	}
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public Suit getSuit() {
		return suit;
	}
	public void setSuit(Suit suit) {
		this.suit = suit;
	}
	public int getDeclarerPosition() {
		return declarerPosition;
	}
	public void setDeclarerPosition(int declarerPosition) {
		this.declarerPosition = declarerPosition;
	}
}

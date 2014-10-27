package common;

public enum Commands {
	GET_LIST_MATCH("GetListMatch"),
	GET_EQUIPES_MATCH("GetEquipesMatch"),
	GET_RESULT_BET("GetResultBet"),
	SET_BET("SetBet"),
	EQUIPE_MATCH("EquipeMatch"),
	LIST_MATCH("ListMatch");
	
	
	private final String value;
	
	private Commands(final String value)
	{
		this.value = value;
	}
	public String toString()
	{
		return value;
	}
}

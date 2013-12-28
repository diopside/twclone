package utilities;

public class Settings {

	
	private boolean displayAllPaths;
	private boolean displayBorders;
	
	public Settings(){
		displayBorders = true;
		displayAllPaths = false;
	}

	public boolean isDisplayAllPaths() {
		return this.displayAllPaths;
	}

	public void setDisplayAllPaths(boolean displayAllPaths) {
		this.displayAllPaths = displayAllPaths;
	}

	public boolean isDisplayBorders() {
		return this.displayBorders;
	}

	public void setDisplayBorders(boolean displayBorders) {
		this.displayBorders = displayBorders;
	}
	
	
	
	
	
	
	
	
}

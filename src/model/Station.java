package model;

public class Station {
	
	private Integer id;
	private float latitude;
	private float longitude;
	private String name;
	private String display_name;
	private Float zone;
	private Integer total_lines;
	private Integer rail;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Float getLatitude() {
		return latitude;
	}
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	public Float getLongitude() {
		return longitude;
	}
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisplayName() {
		return display_name;
	}
	public void setDisplayName(String display_name) {
		this.display_name = display_name;
	}
	public Float getZone() {
		return zone;
	}
	public void setZone(Float zone) {
		this.zone = zone;
	}
	public Integer getTotalLines() {
		return total_lines;
	}
	public void setTotalLines(Integer total_lines) {
		this.total_lines = total_lines;
	}
	public Integer getRail() {
		return rail;
	}
	public void setRail(Integer rail) {
		this.rail = rail;
	}
	
}

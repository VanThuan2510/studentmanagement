package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Province implements Serializable {

	private static final long serialVersionUID = 1L;
	private String province_name;
	private int province_id;
	private static ArrayList<Province> list_provinces;

	public Province(String province_name, int province_id) {
		this.province_name = province_name;
		this.province_id = province_id;
	}

	public String getProvince_name() {
		return province_name;
	}

	public void setProvince_name(String province_name) {
		this.province_name = province_name;
	}

	public int getProvince_id() {
		return province_id;
	}

	public void setProvince_id(int province_id) {
		this.province_id = province_id;
	}

	public static Province getProvinceByID(int ID) {
		for (Province o : list_provinces) {
			if (o.getProvince_id() == ID) {
				return o;
			}
		}
		return null;
	}

	public static Province getProvinceById(int id) {
		return Province.getListProvinces().get(id);
	}

	public static Province getProvinceByName(String name) {
		for (Province o : Province.getListProvinces()) {
			if (o.getProvince_name().equals(name)) {
				return o;
			}
		}
		return null;
	}

	public static ArrayList<Province> getListProvinces() {
		String[] arr_province = { "An Giang", "Bà Rịa – Vũng Tàu", "Bạc Liêu", "Bắc Giang", "Bắc Kạn", "Bắc Ninh",
				"Bến Tre", "Bình Dương", "Bình Định", "Bình Phước", "Bình Thuận", "Cà Mau", "Cao Bằng", "Cần Thơ",
				"Đà Nẵng", "Đắk Lắk", "Đắk Nông", "Điện Biên", "Đồng Nai", "Đồng Tháp", "Gia Lai", "Hà Giang", "Hà Nam",
				"Hà Nội", "Hà Tĩnh", "Hải Dương", "Hải Phòng", "Hậu Giang", "Hòa Bình", "Thành phố Hồ Chí Minh",
				"Hưng Yên", "Khánh Hòa", "Kiên Giang", "Kon Tum", "Lai Châu", "Lạng Sơn", "Lào Cai", "Lâm Đồng",
				"Long An", "Nam Định", "Nghệ An", "Ninh Bình", "Ninh Thuận", "Phú Thọ", "Phú Yên", "Quảng Bình",
				"Quảng Nam", "Quảng Ngãi", "Quảng Ninh", "Quảng Trị", "Sóc Trăng", "Sơn La", "Tây Ninh", "Thái Bình",
				"Thái Nguyên", "Thanh Hóa", "Thừa Thiên Huế", "Tiền Giang", "Trà Vinh", "Tuyên Quang", "Vĩnh Long",
				"Vĩnh Phúc", "Yên Bái" };

		list_provinces = new ArrayList<Province>();
		int i = 1;
		for (String provincename : arr_province) {
			Province province = new Province(provincename, i);
			list_provinces.add(province);
			i++;
		}
		return list_provinces;
	}

	@Override
	public String toString() {
		return "Province [province_name=" + province_name + ", province_id=" + province_id + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(province_id, province_name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Province other = (Province) obj;
		return province_id == other.province_id && Objects.equals(province_name, other.province_name);
	}
	
	

}

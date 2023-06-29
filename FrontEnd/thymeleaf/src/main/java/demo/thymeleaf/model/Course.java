package demo.thymeleaf.model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {
    private int id;
    private String name;
    private String teacher;
    private String imageLink;
    private BigDecimal price;
    private double ratingStar;

    // private Date date;
    public List<Course> toList() {
        return Arrays.asList(
                new Course(1, "Khóa học làm giàu từ con số 1 tỉ", "Người đã bán 1 tỉ gói mè", "",
                        BigDecimal.valueOf(99.89),
                        4.2),
                new Course(1, "Khóa học làm giàu từ con số 1 tỉ", "Người đã bán 1 tỉ gói mè", "",
                        BigDecimal.valueOf(99.89),
                        4.2),
                new Course(1, "Khóa học làm giàu từ con số 1 tỉ", "Người đã bán 1 tỉ gói mè", "",
                        BigDecimal.valueOf(99.89),
                        4.2),
                new Course(1, "Khóa học làm giàu từ con số 1 tỉ", "Người đã bán 1 tỉ gói mè", "",
                        BigDecimal.valueOf(99.89),
                        4.2),
                new Course(1, "Khóa học làm giàu từ con số 1 tỉ", "Người đã bán 1 tỉ gói mè", "",
                        BigDecimal.valueOf(99.89),
                        4.2),
                new Course(1, "Khóa học làm giàu từ con số 1 tỉ", "Người đã bán 1 tỉ gói mè", "",
                        BigDecimal.valueOf(99.89),
                        4.2),
                new Course(1, "Khóa học làm giàu từ con số 1 tỉ", "Người đã bán 1 tỉ gói mè", "",
                        BigDecimal.valueOf(99.89),
                        4.2),
                new Course(1, "Khóa học làm giàu từ con số 1 tỉ", "Người đã bán 1 tỉ gói mè", "",
                        BigDecimal.valueOf(99.89),
                        4.2),
                new Course(1, "Khóa học làm giàu từ con số 1 tỉ", "Người đã bán 1 tỉ gói mè", "",
                        BigDecimal.valueOf(99.89),
                        4.2),
                new Course(1, "Khóa học làm giàu từ con số 1 tỉ", "Người đã bán 1 tỉ gói mè", "",
                        BigDecimal.valueOf(99.89),
                        4.2),
                new Course(1, "Khóa học làm giàu từ con số 1 tỉ", "Người đã bán 1 tỉ gói mè", "",
                        BigDecimal.valueOf(99.89),
                        4.2),
                new Course(1, "Khóa học làm giàu từ con số 1 tỉ", "Người đã bán 1 tỉ gói mè", "",
                        BigDecimal.valueOf(99.89),
                        4.2));
    }
}

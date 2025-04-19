package sys_facturation.com.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
public class sales_details implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long Id;

    private

    @ManyToOne
    Sales sales;

    public sales_details() {
    }
}

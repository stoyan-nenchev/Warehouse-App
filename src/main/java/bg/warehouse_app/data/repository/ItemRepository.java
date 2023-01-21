package bg.warehouse_app.data.repository;

import bg.warehouse_app.data.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {

    @Query("select i from Item i " +
            "where (:name is null or lower(i.name) like lower(concat('%', :name, '%'))) " +
            "and (:code is null or lower(i.code) like lower(concat('%', :code, '%'))) " +
            "and (:category is 'All' or i.category = :category)")
    List<Item> search(@Param("name") String name,
                      @Param("code") String code,
                      @Param("category") String category);

    Optional<Item> getByCode(String code);

}

package tx.sample.unexpected_rollback;

import org.springframework.data.jpa.repository.JpaRepository;

interface ExampleRepository extends JpaRepository<EntityExample, Long> {
}

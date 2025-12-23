@echo off
setlocal ENABLEDELAYEDEXPANSION

REM ======================================
REM Vehicle Service Folder Paths
REM ======================================
set BASE=E:\vehicle-microservices\vehicle-service\vehicle-service\src\main\java\com\vehicle\vehicle_service
set RES=E:\vehicle-microservices\vehicle-service\vehicle-service\src\main\resources

REM Create folders
mkdir "%BASE%"
mkdir "%BASE%\entity"
mkdir "%BASE%\repository"
mkdir "%BASE%\service"
mkdir "%BASE%\controller"
mkdir "%BASE%\feign"
mkdir "%BASE%\dto"
mkdir "%BASE%\event"
mkdir "%RES%"

REM Helper function to write files
REM Usage: call :writefile "path" "content..."
:writefile
(
  for %%A in ("%~2") do echo(%%~A
)>> "%~1"
exit /b


REM ======================================
REM 1) MAIN APPLICATION FILE
REM ======================================
del "%BASE%\VehicleServiceApplication.java" >nul 2>&1
call :writefile "%BASE%\VehicleServiceApplication.java" "package com.vehicle.vehicle_service;" ^
"" ^
"import org.springframework.boot.SpringApplication;" ^
"import org.springframework.boot.autoconfigure.SpringBootApplication;" ^
"import org.springframework.cloud.netflix.eureka.EnableEurekaClient;" ^
"import org.springframework.cloud.openfeign.EnableFeignClients;" ^
"" ^
"@SpringBootApplication" ^
"@EnableEurekaClient" ^
"@EnableFeignClients(basePackages = \"com.vehicle.vehicle_service.feign\")" ^
"public class VehicleServiceApplication {" ^
"    public static void main(String[] args) {" ^
"        SpringApplication.run(VehicleServiceApplication.class, args);" ^
"    }" ^
"}"


REM ======================================
REM 2) ENTITY
REM ======================================
del "%BASE%\entity\Vehicle.java" >nul 2>&1
call :writefile "%BASE%\entity\Vehicle.java" "package com.vehicle.vehicle_service.entity;" ^
"" ^
"import jakarta.persistence.*;" ^
"" ^
"@Entity" ^
"@Table(name = \"vehicles\")" ^
"public class Vehicle {" ^
"" ^
"    @Id" ^
"    @GeneratedValue(strategy = GenerationType.IDENTITY)" ^
"    private Long id;" ^
"" ^
"    private Long customerId;" ^
"    @Column(nullable = false, unique = true)" ^
"    private String vehicleNumber;" ^
"    private String model;" ^
"    private String brand;" ^
"    private Integer year;" ^
"" ^
"    public Vehicle() {}" ^
"" ^
"    public Long getId() { return id; }" ^
"    public void setId(Long id) { this.id = id; }" ^
"" ^
"    public Long getCustomerId() { return customerId; }" ^
"    public void setCustomerId(Long customerId) { this.customerId = customerId; }" ^
"" ^
"    public String getVehicleNumber() { return vehicleNumber; }" ^
"    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }" ^
"" ^
"    public String getModel() { return model; }" ^
"    public void setModel(String model) { this.model = model; }" ^
"" ^
"    public String getBrand() { return brand; }" ^
"    public void setBrand(String brand) { this.brand = brand; }" ^
"" ^
"    public Integer getYear() { return year; }" ^
"    public void setYear(Integer year) { this.year = year; }" ^
"}"


REM ======================================
REM 3) REPOSITORY
REM ======================================
del "%BASE%\repository\VehicleRepository.java" >nul 2>&1
call :writefile "%BASE%\repository\VehicleRepository.java" "package com.vehicle.vehicle_service.repository;" ^
"" ^
"import com.vehicle.vehicle_service.entity.Vehicle;" ^
"import org.springframework.data.jpa.repository.JpaRepository;" ^
"import java.util.Optional;" ^
"import java.util.List;" ^
"" ^
"public interface VehicleRepository extends JpaRepository<Vehicle, Long> {" ^
"    Optional<Vehicle> findByVehicleNumber(String number);" ^
"    List<Vehicle> findByCustomerId(Long customerId);" ^
"}"


REM ======================================
REM 4) DTO
REM ======================================
del "%BASE%\dto\VehicleDto.java" >nul 2>&1
call :writefile "%BASE%\dto\VehicleDto.java" "package com.vehicle.vehicle_service.dto;" ^
"" ^
"public class VehicleDto {" ^
"    public Long id;" ^
"    public Long customerId;" ^
"    public String vehicleNumber;" ^
"    public String model;" ^
"    public String brand;" ^
"    public Integer year;" ^
"}"


REM ======================================
REM 5) FEIGN CLIENT
REM ======================================
del "%BASE%\feign\CustomerClient.java" >nul 2>&1
call :writefile "%BASE%\feign\CustomerClient.java" "package com.vehicle.vehicle_service.feign;" ^
"" ^
"import org.springframework.cloud.openfeign.FeignClient;" ^
"import org.springframework.web.bind.annotation.GetMapping;" ^
"import org.springframework.web.bind.annotation.PathVariable;" ^
"" ^
"@FeignClient(name = \"customer-service\", path = \"/api/customers\")" ^
"public interface CustomerClient {" ^
"" ^
"    @GetMapping(\"/{id}\")" ^
"    Object getCustomerById(@PathVariable(\"id\") Long id);" ^
"}"


REM ======================================
REM 6) EVENT
REM ======================================
del "%BASE%\event\VehicleRegisteredEvent.java" >nul 2>&1
call :writefile "%BASE%\event\VehicleRegisteredEvent.java" "package com.vehicle.vehicle_service.event;" ^
"" ^
"import org.springframework.context.ApplicationEvent;" ^
"import com.vehicle.vehicle_service.entity.Vehicle;" ^
"" ^
"public class VehicleRegisteredEvent extends ApplicationEvent {" ^
"" ^
"    private final Vehicle vehicle;" ^
"" ^
"    public VehicleRegisteredEvent(Object source, Vehicle vehicle) {" ^
"        super(source);" ^
"        this.vehicle = vehicle;" ^
"    }" ^
"" ^
"    public Vehicle getVehicle() {" ^
"        return vehicle;" ^
"    }" ^
"}"


REM ======================================
REM 7) SERVICE
REM ======================================
del "%BASE%\service\VehicleService.java" >nul 2>&1
call :writefile "%BASE%\service\VehicleService.java" "package com.vehicle.vehicle_service.service;" ^
"" ^
"import com.vehicle.vehicle_service.entity.Vehicle;" ^
"import com.vehicle.vehicle_service.repository.VehicleRepository;" ^
"import com.vehicle.vehicle_service.feign.CustomerClient;" ^
"import com.vehicle.vehicle_service.event.VehicleRegisteredEvent;" ^
"import org.springframework.context.ApplicationEventPublisher;" ^
"import org.springframework.stereotype.Service;" ^
"" ^
"import java.util.List;" ^
"import java.util.Optional;" ^
"" ^
"@Service" ^
"public class VehicleService {" ^
"" ^
"    private final VehicleRepository repo;" ^
"    private final CustomerClient customerClient;" ^
"    private final ApplicationEventPublisher publisher;" ^
"" ^
"    public VehicleService(VehicleRepository repo, CustomerClient customerClient, ApplicationEventPublisher publisher) {" ^
"        this.repo = repo;" ^
"        this.customerClient = customerClient;" ^
"        this.publisher = publisher;" ^
"    }" ^
"" ^
"    public Vehicle create(Vehicle v) {" ^
"" ^
"        try {" ^
"            Object cust = customerClient.getCustomerById(v.getCustomerId());" ^
"            if (cust == null) throw new RuntimeException(\"Customer does not exist\");" ^
"        } catch (Exception ex) {" ^
"            throw new RuntimeException(\"Failed to validate customer: \" + ex.getMessage());" ^
"        }" ^
"" ^
"        Vehicle saved = repo.save(v);" ^
"        publisher.publishEvent(new VehicleRegisteredEvent(this, saved));" ^
"        return saved;" ^
"    }" ^
"" ^
"    public List<Vehicle> getAll() { return repo.findAll(); }" ^
"" ^
"    public Optional<Vehicle> getById(Long id) { return repo.findById(id); }" ^
"" ^
"    public Optional<Vehicle> getByNumber(String num) { return repo.findByVehicleNumber(num); }" ^
"" ^
"    public List<Vehicle> getByCustomer(Long custId) { return repo.findByCustomerId(custId); }" ^
"" ^
"    public Vehicle update(Long id, Vehicle newV) {" ^
"        Vehicle v = repo.findById(id).orElseThrow(() -> new RuntimeException(\"Vehicle not found\"));" ^
"" ^
"        v.setBrand(newV.getBrand());" ^
"        v.setModel(newV.getModel());" ^
"        v.setYear(newV.getYear());" ^
"        v.setVehicleNumber(newV.getVehicleNumber());" ^
"" ^
"        return repo.save(v);" ^
"    }" ^
"" ^
"    public void delete(Long id) { repo.deleteById(id); }" ^
"}"


REM ======================================
REM 8) CONTROLLER
REM ======================================
del "%BASE%\controller\VehicleController.java" >nul 2>&1
call :writefile "%BASE%\controller\VehicleController.java" "package com.vehicle.vehicle_service.controller;" ^
"" ^
"import com.vehicle.vehicle_service.entity.Vehicle;" ^
"import com.vehicle.vehicle_service.service.VehicleService;" ^
"import org.springframework.http.ResponseEntity;" ^
"import org.springframework.web.bind.annotation.*;" ^
"" ^
"import java.util.List;" ^
"" ^
"@RestController" ^
"@RequestMapping(\"/api/vehicles\")" ^
"public class VehicleController {" ^
"" ^
"    private final VehicleService service;" ^
"" ^
"    public VehicleController(VehicleService service) {" ^
"        this.service = service;" ^
"    }" ^
"" ^
"    @PostMapping" ^
"    public ResponseEntity<?> create(@RequestBody Vehicle v) {" ^
"        return ResponseEntity.ok(service.create(v));" ^
"    }" ^
"" ^
"    @GetMapping" ^
"    public List<Vehicle> getAll() { return service.getAll(); }" ^
"" ^
"    @GetMapping(\"/{id}\")" ^
"    public ResponseEntity<?> getById(@PathVariable Long id) {" ^
"        return service.getById(id)" ^
"            .map(ResponseEntity::ok)" ^
"            .orElse(ResponseEntity.notFound().build());" ^
"    }" ^
"" ^
"    @GetMapping(\"/number/{num}\")" ^
"    public ResponseEntity<?> getByNumber(@PathVariable String num) {" ^
"        return service.getByNumber(num)" ^
"            .map(ResponseEntity::ok)" ^
"            .orElse(ResponseEntity.notFound().build());" ^
"    }" ^
"" ^
"    @GetMapping(\"/customer/{cid}\")" ^
"    public List<Vehicle> getByCustomer(@PathVariable Long cid) {" ^
"        return service.getByCustomer(cid);" ^
"    }" ^
"" ^
"    @PutMapping(\"/{id}\")" ^
"    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Vehicle v) {" ^
"        return ResponseEntity.ok(service.update(id, v));" ^
"    }" ^
"" ^
"    @DeleteMapping(\"/{id}\")" ^
"    public ResponseEntity<?> delete(@PathVariable Long id) {" ^
"        service.delete(id);" ^
"        return ResponseEntity.ok(\"Deleted\");" ^
"    }" ^
"}"

echo.
echo ===========================================
echo VEHICLE SERVICE FILES GENERATED SUCCESSFULLY
echo ===========================================
endlocal

package ru.sberbank.test.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.sberbank.test.exception.CounterAlreadyExists;
import ru.sberbank.test.exception.CounterNotFoundException;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/counter")
public class CounterRestController {

  private Map<String, Long> counters = new HashMap<>();
  private long result = 0;


  @Operation(description = "Получить уникальные имена счетчиков в виде списка")
  @GetMapping
  public Collection<String> findAllCounters() {
    return counters.keySet();
  }

  @Operation(description = "Получить значения счетчика с указанным именем")
  @GetMapping("/{counterName}")
  public Long findById(@PathVariable String counterName) {
    return Optional.ofNullable(counters.get(counterName)).orElseThrow(CounterNotFoundException::new);
  }

  @Operation(description = "Получить суммарное значение всех счетчиков")
  @GetMapping("/result")
  public Long getResult() {
    return result;
  }

  @Operation(description = "Создать счетчик с уникальным именем")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Long createNewCounter(@NotEmpty @RequestParam final String counterName) {
    if (counters.containsKey(counterName)) throw new CounterAlreadyExists();
    return counters.put(counterName, 0L);
  }

  @Operation(description = "Удалить счетчик с указанным именем")
  @DeleteMapping("/{counterName}")
  @ResponseStatus(HttpStatus.OK)
  public String deleteCounter(@NotNull @PathVariable final String counterName) {
    return Optional.ofNullable(counters.get(counterName))
        .map(e -> {
          result -= e;
          counters.remove(counterName);
          return counterName;
        })
        .orElseThrow(CounterNotFoundException::new);
  }


  @Operation(description = "Инкремент значения счетчика с указанным именем")
  @PutMapping("/{counterName}/increment")
  @ResponseStatus(HttpStatus.OK)
  public Long incrementCounter(@PathVariable("counterName") final String counterName) {
    return Optional.ofNullable(counters.get(counterName)).map(e -> {
      result++;
      return counters.put(counterName, e + 1);
    }).orElseThrow(CounterNotFoundException::new);
  }

}

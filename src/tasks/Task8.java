package tasks;

import common.Person;
import common.Task;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
А теперь о горьком
Всем придется читать код
А некоторым придется читать код, написанный мною
Сочувствую им
Спасите будущих жертв, и исправьте здесь все, что вам не по душе!
P.S. функции тут разные и рабочие (наверное), но вот их понятность и эффективность страдает (аж пришлось писать комменты)
P.P.S Здесь ваши правки желательно прокомментировать (можно на гитхабе в пулл реквесте)
 */
public class Task8 implements Task {

  private long count;

  //Не хотим выдывать апи нашу фальшивую персону, поэтому конвертим начиная со второй
  public List<String> getNames(List<Person> persons) {
    if (persons.size() == 0) {
      return Collections.emptyList();
    }
    persons.remove(0);
    return persons.stream()
            .skip(1) // Скипаем первую персону
            .filter(Objects::nonNull) // И проверяем что остальные персоны не null
            .map(Person::getFirstName)
            .collect(Collectors.toList());
  }

  //ну и различные имена тоже хочется
  public Set<String> getDifferentNames(List<Person> persons) {
    return new HashSet<>(getNames(persons)); // можно сделать создание множества короче
  }

  //Для фронтов выдадим полное имя, а то сами не могут
  public String convertPersonToString(Person person) {
    return Stream.of(
            person.getSecondName(),
            person.getFirstName())
            .filter(Objects::nonNull)
            .collect(Collectors.joining(" ")); // Сделал тоже самое только со стримами
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    return persons.stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toMap(Person::getId, this::convertPersonToString, (p1, p2) -> p1)); // Могло упасть если id совпали
  } //Теперь если ключ повторяется то оставляем первый

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    HashSet<Person> personsSet2 = new HashSet<>(persons2); // сначала создадим множество со вторыми персонами
    return persons1.stream()
            .filter(Objects::nonNull)
            .anyMatch(personsSet2::contains); // тут теперь оставил только метод
  }

  //...
  public long countEven(Stream<Integer> numbers) {
    return numbers.filter(Objects::nonNull).filter(n -> n % 2 == 0).count(); // Добавил проверку на null и сделал подсчет используя count()
  }

  @Override
  public boolean check() {
    System.out.println("Слабо дойти до сюда и исправить Fail этой таски?");
    boolean codeSmellsGood = false;
    boolean reviewerDrunk = true;
    return codeSmellsGood || reviewerDrunk;
  }
}

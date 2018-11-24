package pl.todrzywolek.design.principles;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

enum Relationship {
    PARENT, CHILD, SIBLING
}

class Triplet<T, U, V> {

    private final T first;
    private final U second;
    private final V third;

    public Triplet(T first, U second, V third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public T getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }

    public V getThird() {
        return third;
    }
}

class Person {
    public String name;

    public Person(String name) {
        this.name = name;
    }
}

class Relationships implements RelationshipBrowser { // low-level module related to data storage (does not have any business logic)
    private List<Triplet<Person, Relationship, Person>> relations = new ArrayList<>();

    public void addParentAndChild(Person parent, Person child) {
        relations.add(new Triplet<>(parent, Relationship.PARENT, child));
        relations.add(new Triplet<>(child, Relationship.CHILD, parent));
    }

    public List<Triplet<Person, Relationship, Person>> getRelations() {
        return relations;
    }

    @Override
    public List<Person> findAllChildrenOf(String name) {
        return relations.stream()
                .filter(tr -> Objects.equals(tr.getFirst().name, name)
                        && tr.getSecond() == Relationship.PARENT)
                .map(Triplet::getThird)
                .collect(Collectors.toList());
    }
}

class Research { // high-level module - allows to perform operation on low-level modules - has business logic

    // constructor takes low-level module as dependency - violating Dependency Inversion Principle
    /*public Research(Relationships relationships) {
        List<Triplet<Person, Relationship, Person>> relations = relationships.getRelations();
        relations.stream()
                .filter(tr -> tr.getFirst().name.equals("John")
                        && tr.getSecond() == Relationship.PARENT)
                .forEach(tr -> System.out.println(
                        "John has a child called " + tr.getThird().name
                ));
    }*/

    // constructor that uses abstraction and allows to change implementation in low-level class
    public Research(RelationshipBrowser browser) {
        List<Person> children = browser.findAllChildrenOf("John");
        for (Person child : children) {
            System.out.println("John has a child called " + child.name);
        }
    }
}

class DIPDemo {

    public static void main(String[] args) {
        Person parent = new Person("John");
        Person child1 = new Person("Chris");
        Person child2 = new Person("Matt");

        Relationships relationships = new Relationships();
        relationships.addParentAndChild(parent, child1);
        relationships.addParentAndChild(parent, child2);

        new Research(relationships);

    }
}

// Solution - introduce an abstraction

interface RelationshipBrowser {

    List<Person> findAllChildrenOf(String name);
}
package org.example;

import java.util.*;

import static org.example.PotionStatus.*;

public class Task5 {
    public static void main(String[] args) {
        printResult();
    }

    static void printResult() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Map<Integer, Potion> potionMap = new HashMap<>();
        Potion potionA = new Potion(1, null);
        potionA.setComponentA(1);
        potionA.setStatus(RECALCULATED);
        Potion potionB = new Potion(2, null);
        potionB.setComponentB(1);
        potionB.setStatus(RECALCULATED);
        potionMap.put(potionA.getId(), potionA);
        potionMap.put(potionB.getId(), potionB);
        for (int i = 3; i <= n; i++){
            int k = scanner.nextInt();
            List<Integer> receipt = new ArrayList<>();
            for (int j = 0; j < k; j++){
                receipt.add(scanner.nextInt());
            }
            potionMap.put(i, new Potion(i, receipt));
        }
        potionMap.keySet().forEach(id -> recipeRecalculation(potionMap.get(id), potionMap));
        int q = scanner.nextInt();
        scanner.nextLine(); // переход на новую строку
        List<PotionQuery> potionQueries = new ArrayList<>();
        for(int i = 0; i < q; i++) {
            String[] potionQueryInfo = scanner.nextLine().split(" ");
            potionQueries.add(new PotionQuery(Integer.parseInt(potionQueryInfo[0]),
                    Integer.parseInt(potionQueryInfo[1]), Integer.parseInt(potionQueryInfo[2])));
        }

        printQueriesResult(potionQueries, potionMap);
    }

    static void recipeRecalculation(Potion potion, Map<Integer, Potion> potionMap) {
        if (potion.getReceipt() != null) {
            int addCompA = 0;
            int addCompB = 0;
            potion.setStatus(COMPUTING);
            for (int i = 0; i < potion.getReceipt().size(); i++) {
                Potion component = potionMap.get(potion.getReceipt().get(i));
                if(potion.getStatus() != WRONG) {
                    if (component.getStatus() == CREATED) {
                        if (component.getReceipt() != null) {
                            recipeRecalculation(component, potionMap);
                        }
                        addCompA += component.getComponentA();
                        addCompB += component.getComponentB();
                        if (potion.getStatus() != WRONG) {
                            potion.setStatus(RECALCULATED);
                        }
                    } else if (component.getStatus() == RECALCULATED) {
                        addCompA += component.getComponentA();
                        addCompB += component.getComponentB();
                    } else if (component.getStatus() == COMPUTING) {
                        component.setStatus(WRONG);
                        potion.setStatus(WRONG);
                    } else if (component.getStatus() == WRONG) {
                        potion.setStatus(WRONG);
                    }
                }
            }
            potion.setComponentA(potion.getComponentA() + addCompA);
            potion.setComponentB(potion.getComponentB() + addCompB);
            if (potion.getStatus() != WRONG) {
                potion.setStatus(RECALCULATED);
            }
        }

    }

    static boolean isPotionCreatable(PotionQuery potionQuery, Map<Integer, Potion> potionMap) {
        Potion potion = potionMap.get(potionQuery.getPotionId());
        if (potion != null && potion.getStatus() != WRONG) {
            if (potion.getComponentA() <= potionQuery.getComponentA() && potion.getComponentB() <= potionQuery.getComponentB()) {
                return true;
            }
        }
        return false;
    }

    static void printQueriesResult(List<PotionQuery> potionQueries, Map<Integer, Potion> potionMap) {
        for (PotionQuery potionQuery:potionQueries){
            if (isPotionCreatable(potionQuery, potionMap)) {
                System.out.print(1);
            } else {
                System.out.print(0);
            }
        }
    }
}

enum PotionStatus {
    CREATED,
    COMPUTING,
    RECALCULATED,
    WRONG
}

class Potion {
    private int id;
    private List<Integer> receipt;

    private int componentA = 0;

    private int componentB = 0;

    public PotionStatus getStatus() {
        return status;
    }

    public void setStatus(PotionStatus status) {
        this.status = status;
    }

    private PotionStatus status;


    public Potion(int id, List<Integer> receipt) {
        this.id = id;
        this.receipt = receipt;
        this.status = CREATED;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getComponentA() {
        return componentA;
    }

    public void setComponentA(int componentA) {
        this.componentA = componentA;
    }

    public int getComponentB() {
        return componentB;
    }

    public void setComponentB(int componentB) {
        this.componentB = componentB;
    }

    public int getId() {
        return id;
    }

    public List<Integer> getReceipt() {
        return receipt;
    }

    public void setReceipt(List<Integer> receipt) {
        this.receipt = receipt;
    }
}

class PotionQuery{
    private int componentA;
    private int componentB;
    private int potionId;

    public int getComponentA() {
        return componentA;
    }

    public int getComponentB() {
        return componentB;
    }

    public int getPotionId() {
        return potionId;
    }

    public PotionQuery(int componentA, int componentB, int potionId) {
        this.componentA = componentA;
        this.componentB = componentB;
        this.potionId = potionId;
    }
}

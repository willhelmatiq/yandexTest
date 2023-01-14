package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Task4 {
    public static void main(String[] args) {
        printResult();
    }

    static void printResult() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine(); // переход на новую строку
        List<Order> orderList = new ArrayList<>();
        for (int i = 0; i < n; i++){
            String[] orderInfo = scanner.nextLine().split(" ");
            orderList.add(new Order(Integer.parseInt(orderInfo[0]), Integer.parseInt(orderInfo[1]), Integer.parseInt(orderInfo[2])));
        }
        int k = scanner.nextInt();
        scanner.nextLine(); // переход на новую строку
        List<Query> queryList = new ArrayList<>();
        for (int i = 0; i < k; i++){
            String[] queryInfo = scanner.nextLine().split(" ");
            queryList.add( new Query(Integer.parseInt(queryInfo[0]), Integer.parseInt(queryInfo[1]), Integer.parseInt(queryInfo[2])));
        }
        for (Query query : queryList) {
            printQueryResult(orderList, query);
        }

    }

    static void printQueryResult(List<Order> orderList, Query query) {
        if(query.getQueryType() == 1) {
            System.out.print(orderList.stream().filter(order -> query.getStart() <= order.getStart() && query.getEnd() >= order.getStart())
                    .map(Order::getCost).mapToInt(Integer::intValue).sum() + " ");
        }
        if(query.getQueryType() == 2) {
            System.out.print(orderList.stream().filter(order -> query.getStart() <= order.getEnd() && query.getEnd() >= order.getEnd())
                    .map(order -> order.getEnd() - order.getStart()).mapToInt(Integer::intValue).sum() + " ");
        }
    }
}

class Order{
    private int start;
    private int end;
    private int cost;

    public Order(int start, int end, int cost) {
        this.start = start;
        this.end = end;
        this.cost = cost;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getCost() {
        return cost;
    }
}

class Query {
    private int start;
    private int end;
    private int queryType;

    public Query(int start, int end, int queryType) {
        this.start = start;
        this.end = end;
        this.queryType = queryType;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getQueryType() {
        return queryType;
    }
}

package ua.od.zakhariya.collections;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ListCombinations {

    private static int coresNumber = Runtime.getRuntime().availableProcessors();
    private static long elementsRecursiveEntriesCount = 0;
    private static long listsRecursiveEntriesCount = 0;
    private static long forkJoinListsRecursiveEntriesCount = 0;

    public static void main(String[] args) {
        List<String> strings = new ArrayList<>();
        strings.add("One");
        strings.add("Two");
        strings.add("Three");
        strings.add("Four");
        strings.add("Five");
        strings.add("Six");
        strings.add("Seven");
//        strings.add("Eight");
//        strings.add("Nine");
//        strings.add("Ten");

        Date date = new Date();

        List<List<String>> combinations = combinationsOfList(strings);

        System.out.printf("Count - %s recursions - %s%n%n", combinations.size(), listsRecursiveEntriesCount);

//        for (List<String> combination : combinations) {
//            for (String s : combination) {
//                System.out.println(s);
//            }
//            System.out.println("---");
//        }

        System.out.println(new Date().getTime() - date.getTime() + " millis");
        System.out.println("******");

        date = new Date();

        combinations = combinationsOfElements(strings);

        System.out.printf("Count - %s recursions - %s%n%n", combinations.size(), elementsRecursiveEntriesCount);

//        for (List<String> combination : combinations) {
//            for (String s : combination) {
//                System.out.println(s);
//            }
//            System.out.println("---");
//        }

        System.out.println(new Date().getTime() - date.getTime() + " millis");
        System.out.println("******");

        date = new Date();

        combinations = forkJoinCombinationsOfList(strings);

        System.out.printf("Count - %s recursions - %s%n%n", combinations.size(), forkJoinListsRecursiveEntriesCount);

//        for (List<String> combination : combinations) {
//            for (String s : combination) {
//                System.out.println(s);
//            }
//            System.out.println("---");
//        }

        System.out.println(new Date().getTime() - date.getTime() + " millis");
    }


    private static List<List<String>> combinationsOfElements(List<String> strings) {
        List<List<String>> result = new ArrayList<>();

        elementsRecursiveEntriesCount++;

        if (strings.size() > 0) {
            result.add(strings);
        }

        for (int i = 0; i < strings.size(); i++) {
            String video = strings.get(i);

            List<String> copy = new ArrayList<>(strings);
            copy.remove(video);

            List<List<String>> combinations = combinationsOfElements(copy);

            for (List<String> combination : combinations) {
                if (!result.contains(combination)) {
                    result.add(combination);
                }
            }
        }

        return result;
    }

    private static List<List<String>> combinationsOfList(List<String> strings) {
        List<List<String>> result = new ArrayList<>();

        listsRecursiveEntriesCount++;

        if (strings.size() == 1) {
            result.add(strings);

            return result;
        }

        for (int i = 0; i < strings.size(); i++) {
            String video = strings.get(i);

            List<String> copy = new ArrayList<>(strings);
            copy.remove(video);

            List<List<String>> combinations = combinationsOfList(copy);

            for (List<String> combination : combinations) {
                combination.add(video);
                result.add(combination);
            }
        }

        return result;
    }

    private static List<List<String>> forkJoinCombinationsOfList(List<String> strings) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(coresNumber);

        return forkJoinPool.invoke(new ListCombinations().new ForkJoinCombinator(strings));
    }

    private class ForkJoinCombinator extends RecursiveTask<List<List<String>>> {

        private List<String> strings;

        public ForkJoinCombinator(List<String> strings) {
            this.strings = strings;
        }

        @Override
        protected List<List<String>> compute() {
            List<List<String>> result = new ArrayList<>();
//            ForkJoinCombinator task = new ForkJoinCombinator(strings.subList(1, strings.size()));
//            task.fork();

//            result.addAll(task.join());
            result.addAll(combinations(strings));

            return result;
        }

        private List<List<String>> combinations(List<String> strings) {
            List<List<String>> result = new ArrayList<>();

            forkJoinListsRecursiveEntriesCount++;

            if (strings.size() > 0) {
                result.add(strings);
            }

            for (int i = 0; i < strings.size(); i++) {
                String video = strings.get(i);

                List<String> copy = new ArrayList<>(strings);
                copy.remove(video);

                List<List<String>> combinations = combinations(copy);

                for (List<String> combination : combinations) {
                    if (!result.contains(combination)) {
                        result.add(combination);
                    }
                }
            }

            return result;
        }
    }
}
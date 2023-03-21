package patch;

import gumtree.spoon.diff.operations.Operation;

import java.util.*;

public class OperationGenerator {

    static <T, K> void addItem2MapList(Map<K, List<T>> map, K key, T obj) {
        if(!map.containsKey(key)) {
            map.put(key, new ArrayList<T>());
        }
        map.get(key).add(obj);
    }

    static <T, K> List<T> mergedMap(Map<K, List<T>> map) {
        List<T> list = new ArrayList<>();
        for (List<T> temp : map.values()) {
            list.addAll(temp);
        }
        return list;
    }

    public static List<OperationBean> getOperationBeans(List<Operation> ops) {
        if (ops == null)
            return null;
        Map<String, List<OperationBean>> map = new HashMap<>();
        for (Operation op : ops) {
            OperationBean bean = new OperationBean(op);
            if (bean.getType().equals("Insert")) {
                List<OperationBean> opbs = map.get("Delete");
                if (opbs != null) {
                    int num = opbs.size();
                    for (int k = 0; k < opbs.size(); k++) {
                        OperationBean opdel = opbs.get(k);
                        if (opdel.position == bean.position) {
                            opdel.setType("Update");
                            opdel.setDestination(bean.getDestination());
                            opbs.remove(k);
                            addItem2MapList(map, opdel.getType(), opdel);
                            break;
                        }
                    }
                    if (num != opbs.size()) {
                        continue;
                    }
                }
            }
            if (bean.getType().equals("Move")) {
                List<OperationBean> opbs = map.get("Insert");
                if (opbs != null) {
                    for (int k = 0; k < opbs.size(); k++) {
                        OperationBean opdel = opbs.get(k);
                        if (!opdel.getDestination().equals(bean.getOrigin()) && opdel.position == bean.position) {
                            opdel.setType("Update");
                            opdel.setOrigin(bean.getOrigin());
                            opbs.remove(k);
                            addItem2MapList(map, opdel.getType(), opdel);
                            break;
                        }
                    }
                    continue;
                }
            }
            addItem2MapList(map, bean.getType(), bean);
        }
        return mergedMap(map);
    }
}

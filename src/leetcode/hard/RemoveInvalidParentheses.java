package leetcode.hard;

import java.util.*;

public class RemoveInvalidParentheses {


    public List<String> removeInvalidParentheses(String s) {
        StringBuilder sb1 = new StringBuilder();
        sb1.append(s);

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                break;
            } else if (s.charAt(i) == ')') {
                s = sb1.deleteCharAt(i).toString();
                i--;
            }
        }
        s = sb1.reverse().toString();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ')') {
                break;
            } else if (s.charAt(i) == '(') {
                s = sb1.deleteCharAt(i).toString();
                i--;
            }
        }
        s = sb1.reverse().toString();
        List<String> leftRes = new ArrayList<>();
        if (s.length() == 0) {
            leftRes.add("");
            return leftRes;
        }
        return new ArrayList<>(formatString(s));
    }

    public Set<String> formatString(String s) {
        int left = 0;
        int right = 0;
        int maxCha = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                left++;
            } else if (c == ')') {
                right++;
            }
            if (right > left && maxCha < right - left) {
                maxCha = right - left;
            }

        }
        right -= maxCha;
        List<String> list = new ArrayList<>();
        if (maxCha > 0) {
            list.addAll(deleteNumOfChar(s, maxCha, ')'));
        } else {
            list.add(s);
        }
        if (left > right) {
            for (int i = list.size() - 1; i >= 0; i--) {
                list.addAll(deleteNumOfChar(list.remove(i), left - right, '('));
            }
        }
        return new HashSet<>(list);
    }


    private Set<String> deleteNumOfChar(String s, int numOfDelete, char c) {
        List<Integer> locationOfChar = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c) {
                locationOfChar.add(i);
            }
        }
        List<List<Integer>> indexsList = initIndexList(0, numOfDelete, locationOfChar.size());
        Set<String> set = new HashSet<>();
        for (int i = 0; i < indexsList.size(); i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(s);
            List<Integer> ll = indexsList.get(i);
            for (int k : ll) {
                sb.deleteCharAt(locationOfChar.get(k));
            }
            if (!set.contains(sb.toString())&&isValid(sb.toString())){
                set.add(sb.toString());
            }
        }
        return set;
    }

    private boolean isValid(String s) {
        int left =0;
        int right = 0;
        for (int i = 0;i < s.length();i++){
            if (s.charAt(i) == '('){
                left++;
            }else if (s.charAt(i)==')'){
                right++;
            }
            if (right>left){
                return false;
            }
        }
        return true;
    }


    private List<List<Integer>> initIndexList(int i, int left, int M) {
        List<List<Integer>> list = new ArrayList<>();
        if (left == 1) {
            for (; i <= M - left; i++) {
                List<Integer> r = new ArrayList<>();
                r.add(i);
                list.add(r);
            }
            return list;
        }
        for (; i <= M - left; i++) {

            List<List<Integer>> temp = initIndexList(i + 1, left - 1, M);
            for (List<Integer> l : temp) {
                l.add(i);
            }
            list.addAll(temp);
        }
        return list;
    }

    public static void main(String[] args) {
        new RemoveInvalidParentheses().removeInvalidParentheses("(()");
    }
}

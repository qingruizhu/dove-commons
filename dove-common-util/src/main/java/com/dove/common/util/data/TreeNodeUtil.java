package com.dove.common.util.data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 树状数据结构
 * @Auther: qingruizhu
 * @Date: 2020/4/15 14:06
 */
public class TreeNodeUtil {
    /**
     * 成树
     */
    public static List<? extends Node> convertTree(List<? extends Node> all) {
        List<Node> nodes = all.stream()
                .filter((node -> null == node.getNodeParentId()
                        || 0 == node.getNodeParentId()))//确定根结点
                .map(fatherNode -> seekChildren(fatherNode, all))
                .collect(Collectors.toList());
        return nodes;
//        List<RoleSysNode> nodes = new ArrayList<>();
//        for (TabSysRole role : all) {
//            //确定根结点
//            if (null == role.getParentid() || 0 == role.getParentid()) {
//                RoleSysNode node = seekChildren(role, all);
//                nodes.add(node);
//            }
//        }
//        return nodes;

    }

    /**
     * 寻找子节点/叶子节点
     */
    private static Node seekChildren(Node fatherNode, List<? extends Node> all) {
        Node tmp = fatherNode;
        List<Node> children = all.stream()
                .filter(node -> fatherNode.getNodeId() == node.getNodeParentId())
                .map(node -> seekChildren(node, all))
                .collect(Collectors.toList());
        tmp.setChildren(children);
        return tmp;
//        List < Node > children = new ArrayList<>();
//        for (TabSysRole role : all) {
//            if (fatherRole.getId() == role.getParentid()) {
//                Node seekNode = seekChildren(role, all);
//                children.add(seekNode);
//            }
//        }
//        fatherNode.setChildren(children);
//        return fatherNode;
    }


    public interface Node<T> {
        public Long getNodeId();

        public Long getNodeParentId();

        public List<T> getChildren();

        public void setChildren(List<T> children);
    }
}

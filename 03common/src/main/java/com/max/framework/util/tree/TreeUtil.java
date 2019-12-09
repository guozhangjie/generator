package com.max.framework.util.tree;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.max.framework.core.ognl.OgnlCache;
import com.max.framework.util.string.StringUtil;

import ognl.OgnlException;

/**
 * 将按照level，父id，sort排序的list，转化为树形list
 * @author max
 */
public class TreeUtil {
    /**
     * 日志
     */
    private static final Logger logger = Logger.getLogger(TreeUtil.class);

    /**
     * 创建树形列表
     * @param list 元list
     * @param resultList 树形list
     * @param rootLevel 级别
     * @param levelProName level属性名
     * @param idProName id属性名
     * @param parentIdProName parentId属性名
     * @param <T> 类型
     */
    public static <T> void createListTree(List<T> list, List<T> resultList, int rootLevel, String levelProName, String idProName,
            String parentIdProName) {
        try {
            for (T entity : list) {
                int entityLevel = (int) OgnlCache.getValue(levelProName, entity);
                String idVaule = OgnlCache.getValue(idProName, entity).toString();

                if (entityLevel == rootLevel) {
                    resultList.add(entity);
                    sortList(list, idVaule, resultList, idProName, parentIdProName);
                } else if (entityLevel > rootLevel) {
                    break;
                } else {
                    continue;
                }
            }
        } catch (OgnlException ex) {
            logger.error(ex);
            return;
        }
    }

    /**
     * 
     * @param list 元list
     * @param id 父id的值
     * @param resultList 树形list
     * @param idProName id属性名
     * @param parentIdProName parentId属性名
     * @param <T> 类型
     */
    private static <T> void sortList(List<T> list, String id, List<T> resultList, String idProName, String parentIdProName) {
        try {
            for (T entity : list) {
                String idValue = OgnlCache.getValue(idProName, entity).toString();
                String parentIdValue = StringUtil.toString(OgnlCache.getValue(parentIdProName, entity));
                if (id.equals(parentIdValue)) {
                    resultList.add(entity);
                    sortList(list, idValue, resultList, idProName, parentIdProName);
                }
            }
        } catch (OgnlException ex) {
            logger.error(ex);
            return;
        }
    }

    /**
     * 创建树形列表
     * @param list list
     * @param rootLevel rootLevel
     * @param levelProName levelProName
     * @param idProName idProName
     * @param nameProName nameProName
     * @param parentIdProName parentIdProName
     * @param <T> 类型
     * @return 属性node
     */
    public static <T> List<TreeNodeBean> bulidTree(List<T> list, int rootLevel, String levelProName, String idProName, String nameProName,
            String parentIdProName) {
        List<TreeNodeBean> trees = new ArrayList<TreeNodeBean>();
        try {
            for (T entity : list) {
                int entityLevel = (int) OgnlCache.getValue(levelProName, entity);
                String idVaule = OgnlCache.getValue(idProName, entity).toString();
                String nameValue = OgnlCache.getValue(nameProName, entity).toString();
                String parentIdValue = StringUtil.toString(OgnlCache.getValue(parentIdProName, entity));

                if (entityLevel == rootLevel) {
                    TreeNodeBean treeNode = new TreeNodeBean(idVaule, nameValue, parentIdValue, null);
                    trees.add(findChildren(treeNode, list, idProName, nameProName, parentIdProName));
                }
            }
        } catch (OgnlException ex) {
            logger.error(ex);
            return null;
        }

        return trees;
    }

    /**
     * 查找子treeNode
     * @param treeNode treeNode
     * @param list list
     * @param idProName idProName
     * @param nameProName nameProName
     * @param parentIdProName parentIdProName
     * @param <T> 类型
     * @return treeNode
     * @throws OgnlException OgnlException
     */
    public static <T> TreeNodeBean findChildren(TreeNodeBean treeNode, List<T> list, String idProName, String nameProName, String parentIdProName)
            throws OgnlException {
        for (T entity : list) {
            String idVaule = OgnlCache.getValue(idProName, entity).toString();
            String nameValue = OgnlCache.getValue(nameProName, entity).toString();
            String parentIdValue = StringUtil.toString(OgnlCache.getValue(parentIdProName, entity));

            if (treeNode.getNodeId().equals(parentIdValue)) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<TreeNodeBean>());
                }
                TreeNodeBean it = new TreeNodeBean(idVaule, nameValue, parentIdValue, null);
                treeNode.getChildren().add(findChildren(it, list, idProName, nameProName, parentIdProName));
            }
        }
        return treeNode;
    }
}

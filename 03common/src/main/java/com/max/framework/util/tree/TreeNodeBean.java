package com.max.framework.util.tree;

import java.io.Serializable;
import java.util.List;

/**
 * 属性节点
 * @author max
 */
public class TreeNodeBean implements Serializable {
    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;

    /**
     * nodeId
     */
    private String nodeId;
    
    /**
     * nodeName
     */
    private String nodeName;
    
    /**
     * parentId
     */
    private String parentId;
    
    /**
     * children
     */
    private List<TreeNodeBean> children;
    
    /**
     * 构造器
     * @param nodeId nodeId
     * @param nodeName nodeName
     * @param parentId parentId
     * @param children children
     */
    public TreeNodeBean(String nodeId, String nodeName, String parentId, List<TreeNodeBean> children) {
        super();
        this.nodeId = nodeId;
        this.nodeName = nodeName;
        this.parentId = parentId;
        this.children = children;
    }

    /**
     * nodeId
     * @return nodeId
     */
    public String getNodeId() {
        return nodeId;
    }

    /**
     * nodeId
     * @param nodeId nodeId
     */
    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    /**
     * nodeName 
     * @return nodeName
     */
    public String getNodeName() {
        return nodeName;
    }

    /**
     * nodeName
     * @param nodeName nodeName
     */
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    /**
     * children
     * @return children
     */
    public List<TreeNodeBean> getChildren() {
        return children;
    }

    /**
     * children
     * @param children children
     */
    public void setChildren(List<TreeNodeBean> children) {
        this.children = children;
    }

    /**
     * parentId
     * @return parentId
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * parentId
     * @param parentId parentId
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return "TreeNodeBean [nodeId=" + nodeId + ", nodeName=" + nodeName + ", parentId=" + parentId + "]";
    }
}

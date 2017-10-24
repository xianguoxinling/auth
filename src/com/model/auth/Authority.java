package com.model.auth;

public class Authority
{
	private String id = null;
	private String name = null;
	private String target = null;
	private String explain = null;
	
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getExplain()
	{
		return explain;
	}
	public void setExplain(String explain)
	{
		this.explain = explain;
	}
    public String getTarget()
    {
        return target;
    }
    public void setTarget(String target)
    {
        this.target = target;
    }
	
}

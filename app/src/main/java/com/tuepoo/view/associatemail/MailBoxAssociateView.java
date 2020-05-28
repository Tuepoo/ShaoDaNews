package com.tuepoo.view.associatemail;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.MultiAutoCompleteTextView;

/**********************************************************
 * @文件名称：MailBoxAssociateView.java
 * @文件描述：本在邮箱联想控件，输入@符后开始联想
 **********************************************************/
public class MailBoxAssociateView extends MultiAutoCompleteTextView
{
	public MailBoxAssociateView(Context context)
	{
		super(context);
	}

	public MailBoxAssociateView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public MailBoxAssociateView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	@Override
	public boolean enoughToFilter()
	{
		return getText().toString().contains("@") && getText().toString().indexOf("@") > 0;
	}
}
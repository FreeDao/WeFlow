PushMsgUtil pushmsg = new PushMsgUtil(handler, 0x88661256);
            String sendMSG = "{\"n_content\":\"\", " +
					"\"n_extras\": {\"msgtype\": \"1\", \"msgcontent\" : \"" + "����" + "\"," +
					"\"msghint\" : \"" + "����鿴" + "\"," + 
					"\"msgtitle\" : \"" + "������Ϣ" + "\"" +"}}";
            pushmsg.execute(sendMSG, "weflow");
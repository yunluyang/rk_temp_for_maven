package utils;

import event.Q6;
import event.WA;

public class UpsUtils {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	UpsUtils.getQ6Data("283233362E30203030302E30203030302E302035302E30203233352E39202D2D2D2E2D202D2D2D2E2D2035302E3020303031202D2D2D202D2D2D203230322E36202D2D2D2E2D2032332E36203939393939203130302032302030303030303030302030303030303030302031310D".trim());
		UpsUtils.getWAData("283030302E30202D2D2D2E2D202D2D2D2E2D203030302E33202D2D2D2E2D202D2D2D2E2D203030302E30203030302E33203030312E34202D2D2D2E2D202D2D2D2E2D203030302030303130303030300D".trim());
	}
	
	
	//(MMM.M MMM.M MMM.M NN.N PPP.P PPP.P PPP.P RR.R QQQ QQQ QQQ SSS.S VVV.V TT.T ttttt CCC KB ffffffff wwwwwwww YO<cr>

	public static Q6 getQ6Data(String info) {
		int m=info.length()/2;
		if(m*2<info.length()){
			m++;
			}
		String[] strs=new String[m];
			int j=0;
			for(int i=0;i<info.length();i++){
					if(i%2==0){//每隔两个
							strs[j]=""+info.charAt(i);
					}else{
						strs[j]=strs[j]+" "+info.charAt(i);//将字符加上两个空格
						j++;
					}           
		}
		if(info.startsWith("28")&&info.endsWith("0D")) {
			StringBuilder sb = new StringBuilder();
			Q6 q6 = new Q6();
			int i=0;
			for(String s:strs) {
				s= s.replace(" ", "");
				switch(s) {
					case "28":
						i++;
						break;
					case "0D":
						i++;
						break;
					case "2D":
						sb.append("0");
						break;
					case "2E":
						sb.append(".");
						break;
					case "20":
					switch(i) {
						case 0:
							sb.append("开始解析");
							break;
						case 1:
							q6.setInputVoltageR(sb.toString()+"V");
							break;
						case 2:
							q6.setInputVoltageS(sb.toString()+"V");
							break;
						case 3:
							q6.setInputVoltageT(sb.toString()+"V");
							break;
						case 4:
							q6.setInputFrequency(sb.toString()+"Hz");
							break;
						case 5:
							q6.setOutputVoltageR(sb.toString()+"V");
							break;
						case 6:
							q6.setOutputVoltageS(sb.toString()+"V");
							break;
						case 7:
							q6.setOutputVoltageT(sb.toString()+"V");
							break;
						case 8:
							q6.setOutputFrequency(sb.toString()+"Hz");
							break;
						case 9:
							q6.setOutputCurrent(sb.toString()+"%");
							sb.append("=QQQ");
							break;
						case 10:
							sb.append("=QQQ");
							break;
						case 11:
							sb.append("=QQQ");
							break;
						case 12:
							q6.setBatteryPercentage(sb.toString()+"V");
							break;
						case 13:
							q6.setBatteryNegative(sb.toString()+"V");
							break;
						case 14:
							q6.setTemp(sb.toString()+"°");
							break;
						case 15:
							q6.setBatteryTime(sb.toString()+"S");
							break;
						case 16:
							q6.setBatteryPercentage(sb.toString()+"%");
							break;
						case 17:
							q6.setKB(sb.toString());
							break;
						case 18:
							q6.setErrorCode(sb.toString());
							break;
						case 19:
							q6.setWarrnigCode(sb.toString());
							break;
						}
						sb.delete(0, sb.length());
						i++;
						break;
					default :
						sb.append(s.charAt(1));
						break;
				
				}
			}
			return q6;
		}else {
			System.out.println("不符合格式要求");
			return null;
		}
	}
	
	//28 30 30 30 2E 30 20 2D 2D 2D 2E 2D 20 2D 2D 2D 2E 2D 20 30 30 30 2E 33 20 2D 2D 2D 2E 2D 20 2D 2D 2D 2E 2D 20 30 30 30 2E 30 20 30 30 30 2E 33 20 30 30 31 2E 34 20 2D 2D 2D 2E 2D 20 2D 2D 2D 2E 2D 20 30 30 30 20 30 30 31 30 30 30 30 30 0D 
	public static WA getWAData(String info) {
		
		int m=info.length()/2;
		if(m*2<info.length()){
			m++;
			}
		String[] strs=new String[m];
			int j=0;
			for(int i=0;i<info.length();i++){
					if(i%2==0){//每隔两个
							strs[j]=""+info.charAt(i);
					}else{
						strs[j]=strs[j]+" "+info.charAt(i);//将字符加上两个空格
						j++;
					}           
		}
		if(info.startsWith("28")&&info.endsWith("0D")) {
			StringBuilder sb = new StringBuilder();
			WA wa = new WA();
			int i=0;
			for(String s:strs) {
				s= s.replace(" ", "");
				switch(s) {
					case "28":
						i++;
						break;
					case "0D":
						wa.setUpsStatus(sb.toString());
						i++;
						break;
					case "2D":
						sb.append("0");
						break;
					case "2E":
						sb.append(".");
						break;
					case "20":
					switch(i) {
						case 0:
							sb.append("开始解析");
							break;
						case 1:
							wa.setOutPutPower(sb.toString()+"KW");
							break;
						case 2:
							break;
						case 3:
							break;
						case 4:
							wa.setOutPutApparentPower(sb.toString()+"KW");
							break;
						case 5:
							break;
						case 6:
							break;
						case 7:
							wa.setTotalPower(sb.toString()+"KW");
							break;
						case 8:
							wa.setTotalApparentPower(sb.toString()+"KVA");
							break;
						case 9:
							wa.setOutputCurrent(sb.toString()+"A");
							break;
						case 10:
							break;
						case 11:
							break;
						case 12:
							wa.setOutputLoadPercentage(sb.toString()+"%");
							break;
						case 13:
							break;
						default :
							break;
						}
						sb.delete(0, sb.length());
						i++;
						break;
					default :
						sb.append(s.charAt(1));
						break;
				
				}
			}
			return wa;
		}else {
			System.out.println("不符合格式要求");
			return null;
		}
	}
}

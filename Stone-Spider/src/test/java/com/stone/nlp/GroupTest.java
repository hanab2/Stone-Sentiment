package com.stone.nlp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class GroupTest {
    public static void main(String[] args) {
        //请求头中的token
        String token = "1e8f6f01f11a47118ef2c0eb25ebfbd31608100940719token";
        //申请的接口地址
        String url = "http://comdo.hanlp.com/hanlp/v1/textAnalysis/classification";
        //所有参数
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("text", "这是一组严峻的数字：我国肺癌每年新增发病约78.7万人，死亡约63.1万人，相当于每一分钟就有1.5人患上肺癌，约占所有恶性肿瘤新发病例的五分之一\n" +
                "\n" +
                "　　目前，我国肿瘤患者的诊疗模式，大部分还是谁第一个“抓到”患者就由谁来治疗。取决于首诊的是外科还是内科，患者拿到的治疗方案各有不同，甚至是相悖的\n" +
                "\n" +
                "　　新冠肺炎会不会转化为肺癌？疫情防控常态化会给肺癌患者带来什么影响？这是疫情触发的肺癌防治新问题\n" +
                "\n" +
                "　　17年来，创办“抗癌厨房”的万佐成和熊庚香夫妇，见证了数以万计的癌症家庭经历辗转求医、反复治疗的苦楚。\n" +
                "\n" +
                "　　“地方上不会看、看不了，最后还是要到肿瘤医院来。”万佐成总结道。\n" +
                "\n" +
                "　　“抗癌厨房”其实是一间露天小吃店，开在与江西省肿瘤医院一墙之隔的小巷里。大家称它为“抗癌厨房”，是因为做饭的都是肿瘤医院的病人及其家属。\n" +
                "\n" +
                "　　年过六旬的万佐成已经成为“半个”癌症专家。据他观察，肺癌病人是最多的，因为“抽烟的人多嘛，还有这个癌跟环境污染有关”。上了年纪的老年人最多，也有三四十岁的中青年。他们有一个共同的特征，发现时基本上到了中晚期。\n" +
                "\n" +
                "　　新冠肺炎疫情下，呼吸健康受到前所未有的关注。疫情最严重的时候，万佐成关闭了“抗癌厨房”，改成自己下厨，然后无接触地为患者送餐。\n" +
                "\n" +
                "　　“得病的人心情好，才会好得快。”他对新华每日电讯记者说，肺癌不传染，希望大家不要歧视患者，要给他们战胜病魔的希望。\n" +
                "\n" +
                "　　肺癌发病人群呈“两头翘”的趋势\n" +
                "\n" +
                "　　这是一组严峻的数字：我国肺癌每年新增发病约78.7万人，死亡约63.1万人，相当于每一分钟就有1.5人患上肺癌，约占所有恶性肿瘤新发病例的五分之一。\n" +
                "\n" +
                "　　在日前举行的中美国际肺癌多学科论坛上，国家癌症中心主任、中国医学科学院肿瘤医院院长赫捷院士说，肺癌已成为严重危害我国人民健康的“第一杀手”。其领衔的肺癌诊疗多学科团队的最新调查显示，肺癌发病率在40岁以上人群中急剧上升，在80至84岁年龄段达到峰值。\n" +
                "\n" +
                "　　对于中国来说，疫情防控常态化将长远影响一系列肺部疾病的防治工作。肺癌无疑是其中最为艰巨的挑战——连续10年发病人数和死亡人数位居恶性肿瘤之首，发病人群呈现中青年和老年人“两头翘”的趋势。\n" +
                "\n" +
                "　　该调查还显示，我国男性肺癌发病率和死亡率显著高于女性。此外，城市地区男性肺癌发病率低于农村地区，而女性肺癌发病率则相反。\n" +
                "\n" +
                "　　国家癌症中心癌症筛查与早诊早治办公室开展的多中心回顾性流行病学调查则表明，根据2005到2014年的10年数据分析，我国肺癌防治情况呈现以下趋势：老年患者、女性患者、晚期疾病患者比例增加；吸烟率在下降，但吸烟暴露持续高水平；腺癌取代鳞癌成为主要病理类型；医疗费用大幅增加等。\n" +
                "\n" +
                "　　随着社会经济的快速发展和人口的老龄化，我国肺癌防治工作也在“升级”。原卫生部于2008年启动了国家癌症登记和跟踪计划，癌症登记处的数量从2008年的95个增加到2019年的574个。面向大规模高危人群的城市癌症和农村癌症早诊早治项目分别在2012年和2005年启动。去年，健康中国行动宣布开展面向2030年的癌症防治行动，明确了提高总体癌症5年生存率等一系列“硬指标”。\n" +
                "\n" +
                "　　权威肿瘤专家疾呼，尽管医学进步带来了战胜疾病的希望，但呼吸健康仍是一个公共卫生问题，需要全社会携手应对。\n" +
                "\n" +
                "　　现实困境：首诊科室决定治疗方案\n" +
                "\n" +
                "　　这是一个揪心的故事：一位从事胸外科工作17年的医生发现，做了半辈子机械维修工的父亲得了肺癌，发现时已淋巴结转移。有职业暴露史的父亲是肺癌高危人群，为啥自己却忘记了？自责和心疼让这位医生泪如雨下。\n" +
                "\n" +
                "　　“吸烟、职业暴露史、家族病史，以及慢阻肺、弥漫性肺纤维化病史，是罹患肺癌最高危的因素，但现实中筛查覆盖率不够。”权威临床专家、中国医学科学院肿瘤医院内科主任王洁说，我国肺癌的筛查、诊断技术已取得实质性改善。目前低剂量螺旋CT（LDCT）是较为准确的筛查技术，高分辨率CT则是最重要的诊断技术之一。新技术的应用实现了肺癌精准诊断，使得早预防、早发现、早诊断、早治疗成为可能。\n" +
                "\n" +
                "　　一名中年患者给王洁留下深刻印象：看病的时候，他递上厚厚一摞CT胸片和病历本，写着来自不同医院、不同医生的各种治疗方案。这名患者越治疗越觉得无所适从，加上恐慌心理，几乎要崩溃了。\n" +
                "\n" +
                "　　这是肿瘤患者普遍面临的一个困境。目前，我国肿瘤患者的诊疗模式，大部分还是谁第一个“抓到”患者就由谁来治疗。取决于首诊的是外科还是内科，患者拿到的治疗方案各有不同，甚至是相悖的。\n" +
                "\n" +
                "　　“在这样的治疗模式下，患者有可能接受重复检查、非最佳治疗、或错过最佳治疗时机。”王洁说。\n" +
                "\n" +
                "　　后来，这位中年患者接受了多学科团队的集体会诊。专家建议患者先进行一种新辅助治疗，而不是先手术。完成新辅助治疗后，患者肿瘤明显缩小，纵隔淋巴结也同步缩小。后续顺利地完成了手术，并根据术后病理报告完成了后续的规范治疗。\n" +
                "\n" +
                "　　王洁表示，开展精准预防、多学科诊疗是推进我国肺癌防治的关键。通过多学科诊疗团队的集体努力，可以使肿瘤患者的治疗更加精准、及时、规范，并显著改善患者预后。\n" +
                "\n" +
                "　　发病率高、五年生存率低，让肺癌治疗是世界性难题。在中美国际肺癌多学科论坛上，多位国内外院士专家表示，不管是“化疗+手术”的老办法，还是免疫治疗、靶向治疗等新技术，提高肺癌整体疗效在于推广多学科诊疗模式（MDT）。\n" +
                "\n" +
                "　　从2018年起，我国已全面开展肿瘤多学科诊疗试点工作，要求试点的三级综合医院和肿瘤专科医院建立MDT标准化操作流程，并逐步扩大病种范围。今年，中国医师协会成立肿瘤多学科诊疗专业委员会，推动专科医院、基层医院开展MDT建设，落地“多学科参与，上下级协作”的肿瘤诊疗模式。\n" +
                "\n" +
                "　　作为肿瘤多学科诊疗专委会主任委员，王洁认为，MDT模式已成为欧美医疗体系的常态，但我国仅有部分三级综合/专科医院建立了MDT，且使用率不高；二级综合性医院则全部没有建立MDT。要破解肿瘤患者收治无序、治疗和预后水平不佳等痼疾，依然任重道远。\n" +
                "\n" +
                "　　“未来，希望基于肿瘤多学科诊疗平台，行业内可以开展更多能够落地的系列项目，培训肺癌领域医生多学科协作的理念，进一步提升不同层级医生的水平。”王洁说。\n" +
                "\n" +
                "　　新冠肺炎与肺癌关联性尚不明确\n" +
                "\n" +
                "　　新冠肺炎会不会转化为肺癌？疫情防控常态化会给肺癌患者带来什么影响？这是疫情触发的肺癌防治新问题。\n" +
                "\n" +
                "　　王洁说，肺癌很容易引起肺炎，慢阻性肺炎与肺癌的发生也可能有一定相关性，但新冠肺炎与肺癌的关联性“短时间内还缺少科学证据”。\n" +
                "\n" +
                "　　“目前唯一可以明确的是，如果是一个肿瘤病人感染了新冠肺炎，那么其发生重症几率是增高的。”她建议，肿瘤病人在疫情下应做好防护措施，更好地保护自己。\n" +
                "\n" +
                "　　值得注意的是，随着疫情让人们的健康意识增强，不少人在体检中发现了肺癌或肺部小结节。其中，40岁以下的年轻患者在快速增长。\n" +
                "\n" +
                "　　王洁说，只有10%到20%的肺结节是早期肺癌，大家不用过分紧张。即使是肺癌，只要积极治疗，也可以实现慢病化生存。\n" +
                "\n" +
                "　　从每天凌晨四点开始，一直要忙活要到晚上九点，日复一日。万佐成说自己就像一部慢慢走动的钟表，但他要一直走下去，因为“要让他们吃饱饭，再好好坚持下去”。\n" +
                "\n" +
                "　　时间对于癌症病人来说，意味着希望。\n" +
                "\n" +
                "　　中国医学科学院肿瘤医院内科副主任医师段建春表示，近10年的数据研究表明，肺癌发病“两头翘”的趋势，给患者家庭和社会带来了日益沉重的医疗负担。“十四五”规划实施期间，中国应首要开展控烟和环境污染治理，并加强相关职业防护，加大对全人群的健康防癌宣教。肺癌防治应决胜于“疾病未发生时”。\n" +
                "\n" +
                "　　那位自责的胸外科医生等到了希望：年逾80岁的父亲在接受了多学科联合的精准治疗后，又重新站了起来，勇敢地继续与病魔斗争。（记者屈婷）");
        //执行api
        String result = doHanlpApi(token, url, params);
        System.out.println(result);
    }

    public static String doHanlpApi(String token, String url, Map<String, Object> params) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            //添加header请求头，token请放在header里
            httpPost.setHeader("token", token);
            // 创建参数列表
            List<NameValuePair> paramList = new ArrayList<>();
            if (params != null) {
                for (String key : params.keySet()) {
                    //所有参数依次放在paramList中
                    paramList.add(new BasicNameValuePair(key, (String) params.get(key)));
                }
                //模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "utf-8");
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            String resultString = EntityUtils.toString(response.getEntity(), "utf-8");
            return resultString;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}

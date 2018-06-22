package com.example.leeroyapp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureMockRestServiceServer
public class LeeroyAppApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private MockRestServiceServer server;

	@Test
	public void contextLoads() throws Exception {
		this.mockMvc.perform(get("/api"))
				.andExpect(status().isOk())
				.andExpect(content().string("hello 127.0.0.1 api response from leeroy!!"));

		this.mockMvc.perform(get("/name"))
				.andExpect(status().isOk())
				.andExpect(content().string("hello 127.0.0.1 my name is leeroy"));
	}

  @Test
  public void metadata() throws Exception {
    server
        .expect(once(), requestTo("http://169.254.170.2/v2/metadata"))
        .andExpect(method(HttpMethod.GET))
        .andRespond(
            withSuccess(
                "{\"Cluster\":\"arn:aws:ecs:us-east-1:353295783710:cluster/tf-ecs-cluster\",\"TaskARN\":\"arn:aws:ecs:us-east-1:353295783710:task/ad117b4a-1c8f-4126-b8f5-0aa6fe14a40d\",\"Family\":\"foobar\",\"Revision\":\"1\",\"DesiredStatus\":\"RUNNING\",\"KnownStatus\":\"RUNNING\",\"Containers\":[{\"DockerId\":\"0eac38065c1468044403dc5e2624cf2196c430d5aa86a60720d0c63f3dfeb049\",\"Name\":\"~internal~ecs~pause\",\"DockerName\":\"ecs-foobar-1-internalecspause-b2ddc9fbd8ebd097e201\",\"Image\":\"fg-proxy:tinyproxy\",\"ImageID\":\"\",\"Labels\":{\"com.amazonaws.ecs.cluster\":\"arn:aws:ecs:us-east-1:353295783710:cluster/tf-ecs-cluster\",\"com.amazonaws.ecs.container-name\":\"~internal~ecs~pause\",\"com.amazonaws.ecs.task-arn\":\"arn:aws:ecs:us-east-1:353295783710:task/ad117b4a-1c8f-4126-b8f5-0aa6fe14a40d\",\"com.amazonaws.ecs.task-definition-family\":\"foobar\",\"com.amazonaws.ecs.task-definition-version\":\"1\"},\"DesiredStatus\":\"RESOURCES_PROVISIONED\",\"KnownStatus\":\"RESOURCES_PROVISIONED\",\"Limits\":{\"CPU\":0,\"Memory\":0},\"CreatedAt\":\"2018-06-22T03:24:47.584657841Z\",\"StartedAt\":\"2018-06-22T03:24:48.244738153Z\",\"Type\":\"CNI_PAUSE\",\"Networks\":[{\"NetworkMode\":\"awsvpc\",\"IPv4Addresses\":[\"172.17.1.173\"]}]},{\"DockerId\":\"90dff2737c6c78bd72920fdaef71f764233f6a34e3b350efb0dc340bf22ce39d\",\"Name\":\"foobar\",\"DockerName\":\"ecs-foobar-1-foobar-b081c3bcdcd2e4ee6000\",\"Image\":\"jamorin/leeroy-java-microservice:latest\",\"ImageID\":\"sha256:f3db59cee9952169ec3ec569c8d72201b889f173e00fe71d4db590402cdec068\",\"Labels\":{\"com.amazonaws.ecs.cluster\":\"arn:aws:ecs:us-east-1:353295783710:cluster/tf-ecs-cluster\",\"com.amazonaws.ecs.container-name\":\"foobar\",\"com.amazonaws.ecs.task-arn\":\"arn:aws:ecs:us-east-1:353295783710:task/ad117b4a-1c8f-4126-b8f5-0aa6fe14a40d\",\"com.amazonaws.ecs.task-definition-family\":\"foobar\",\"com.amazonaws.ecs.task-definition-version\":\"1\"},\"DesiredStatus\":\"RUNNING\",\"KnownStatus\":\"RUNNING\",\"Limits\":{\"CPU\":256,\"Memory\":512},\"CreatedAt\":\"2018-06-22T03:24:55.363085643Z\",\"StartedAt\":\"2018-06-22T03:24:55.790784914Z\",\"Type\":\"NORMAL\",\"Networks\":[{\"NetworkMode\":\"awsvpc\",\"IPv4Addresses\":[\"172.17.1.173\"]}]}],\"Limits\":{\"CPU\":0.25,\"Memory\":512},\"PullStartedAt\":\"2018-06-22T03:24:48.516978124Z\",\"PullStoppedAt\":\"2018-06-22T03:24:55.360534329Z\"}", MediaType.APPLICATION_JSON));

		String body = this.mockMvc.perform(get("/metadata"))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse().getContentAsString();

		Map json = new ObjectMapper().readValue(body, Map.class);
		assertThat(json.get("Cluster")).isEqualTo("arn:aws:ecs:us-east-1:353295783710:cluster/tf-ecs-cluster");
	}

	@Test
	public void stats() throws Exception {
    server
        .expect(once(), requestTo("http://169.254.170.2/v2/stats"))
        .andExpect(method(HttpMethod.GET))
        .andRespond(
            withSuccess(
                "{\"0eac38065c1468044403dc5e2624cf2196c430d5aa86a60720d0c63f3dfeb049\":{\"read\":\"2018-06-22T18:37:02.636823497Z\",\"preread\":\"2018-06-22T18:37:01.64408668Z\",\"num_procs\":0,\"pids_stats\":{},\"network\":{},\"memory_stats\":{\"stats\":{\"cache\":3489792,\"mapped_file\":2121728,\"total_inactive_file\":696320,\"pgpgout\":1506,\"rss\":3796992,\"total_mapped_file\":2121728,\"pgpgin\":3285,\"pgmajfault\":31,\"total_rss\":3796992,\"hierarchical_memory_limit\":536870912,\"total_pgfault\":3024,\"total_active_file\":2793472,\"active_anon\":3796992,\"total_active_anon\":3796992,\"total_pgpgout\":1506,\"total_cache\":3489792,\"active_file\":2793472,\"pgfault\":3024,\"inactive_file\":696320,\"total_pgpgin\":3285,\"hierarchical_memsw_limit\":9223372036854771712},\"max_usage\":11767808,\"usage\":8835072,\"limit\":4144832512},\"blkio_stats\":{\"io_service_bytes_recursive\":[{\"major\":202,\"minor\":26368,\"op\":\"Read\",\"value\":135168},{\"major\":202,\"minor\":26368,\"op\":\"Write\"},{\"major\":202,\"minor\":26368,\"op\":\"Sync\"},{\"major\":202,\"minor\":26368,\"op\":\"Async\",\"value\":135168},{\"major\":202,\"minor\":26368,\"op\":\"Total\",\"value\":135168},{\"major\":253,\"minor\":1,\"op\":\"Read\",\"value\":135168},{\"major\":253,\"minor\":1,\"op\":\"Write\"},{\"major\":253,\"minor\":1,\"op\":\"Sync\"},{\"major\":253,\"minor\":1,\"op\":\"Async\",\"value\":135168},{\"major\":253,\"minor\":1,\"op\":\"Total\",\"value\":135168},{\"major\":253,\"minor\":2,\"op\":\"Read\",\"value\":135168},{\"major\":253,\"minor\":2,\"op\":\"Write\"},{\"major\":253,\"minor\":2,\"op\":\"Sync\"},{\"major\":253,\"minor\":2,\"op\":\"Async\",\"value\":135168},{\"major\":253,\"minor\":2,\"op\":\"Total\",\"value\":135168},{\"major\":253,\"minor\":5,\"op\":\"Read\",\"value\":3469312},{\"major\":253,\"minor\":5,\"op\":\"Write\"},{\"major\":253,\"minor\":5,\"op\":\"Sync\"},{\"major\":253,\"minor\":5,\"op\":\"Async\",\"value\":3469312},{\"major\":253,\"minor\":5,\"op\":\"Total\",\"value\":3469312}],\"io_serviced_recursive\":[{\"major\":202,\"minor\":26368,\"op\":\"Read\",\"value\":33},{\"major\":202,\"minor\":26368,\"op\":\"Write\"},{\"major\":202,\"minor\":26368,\"op\":\"Sync\"},{\"major\":202,\"minor\":26368,\"op\":\"Async\",\"value\":33},{\"major\":202,\"minor\":26368,\"op\":\"Total\",\"value\":33},{\"major\":253,\"minor\":1,\"op\":\"Read\",\"value\":33},{\"major\":253,\"minor\":1,\"op\":\"Write\"},{\"major\":253,\"minor\":1,\"op\":\"Sync\"},{\"major\":253,\"minor\":1,\"op\":\"Async\",\"value\":33},{\"major\":253,\"minor\":1,\"op\":\"Total\",\"value\":33},{\"major\":253,\"minor\":2,\"op\":\"Read\",\"value\":33},{\"major\":253,\"minor\":2,\"op\":\"Write\"},{\"major\":253,\"minor\":2,\"op\":\"Sync\"},{\"major\":253,\"minor\":2,\"op\":\"Async\",\"value\":33},{\"major\":253,\"minor\":2,\"op\":\"Total\",\"value\":33},{\"major\":253,\"minor\":5,\"op\":\"Read\",\"value\":278},{\"major\":253,\"minor\":5,\"op\":\"Write\"},{\"major\":253,\"minor\":5,\"op\":\"Sync\"},{\"major\":253,\"minor\":5,\"op\":\"Async\",\"value\":278},{\"major\":253,\"minor\":5,\"op\":\"Total\",\"value\":278}]},\"cpu_stats\":{\"cpu_usage\":{\"percpu_usage\":[1118071083,1185898947,0,0,0,0,0,0,0,0,0,0,0,0,0],\"usage_in_usermode\":60000000,\"total_usage\":2303970030,\"usage_in_kernelmode\":550000000},\"system_cpu_usage\":110597470000000,\"throttling_data\":{}},\"precpu_stats\":{\"cpu_usage\":{\"percpu_usage\":[1118057737,1185898947,0,0,0,0,0,0,0,0,0,0,0,0,0],\"usage_in_usermode\":60000000,\"total_usage\":2303956684,\"usage_in_kernelmode\":550000000},\"system_cpu_usage\":110595490000000,\"throttling_data\":{}},\"storage_stats\":{}},\"90dff2737c6c78bd72920fdaef71f764233f6a34e3b350efb0dc340bf22ce39d\":{\"read\":\"2018-06-22T18:37:02.64429449Z\",\"preread\":\"2018-06-22T18:37:01.636775382Z\",\"num_procs\":0,\"pids_stats\":{},\"network\":{},\"memory_stats\":{\"stats\":{\"cache\":48160768,\"mapped_file\":12001280,\"total_inactive_file\":31215616,\"pgpgout\":225219,\"rss\":193949696,\"total_mapped_file\":12001280,\"pgpgin\":284328,\"pgmajfault\":90,\"total_rss\":193949696,\"hierarchical_memory_limit\":536870912,\"total_pgfault\":279585,\"total_active_file\":16945152,\"active_anon\":193949696,\"total_active_anon\":193949696,\"total_pgpgout\":225219,\"total_cache\":48160768,\"active_file\":16945152,\"pgfault\":279585,\"inactive_file\":31215616,\"total_pgpgin\":284328,\"hierarchical_memsw_limit\":1073741824},\"max_usage\":267935744,\"usage\":244244480,\"limit\":536870912},\"blkio_stats\":{\"io_service_bytes_recursive\":[{\"major\":202,\"minor\":26368,\"op\":\"Read\",\"value\":286720},{\"major\":202,\"minor\":26368,\"op\":\"Write\"},{\"major\":202,\"minor\":26368,\"op\":\"Sync\"},{\"major\":202,\"minor\":26368,\"op\":\"Async\",\"value\":286720},{\"major\":202,\"minor\":26368,\"op\":\"Total\",\"value\":286720},{\"major\":253,\"minor\":1,\"op\":\"Read\",\"value\":286720},{\"major\":253,\"minor\":1,\"op\":\"Write\"},{\"major\":253,\"minor\":1,\"op\":\"Sync\"},{\"major\":253,\"minor\":1,\"op\":\"Async\",\"value\":286720},{\"major\":253,\"minor\":1,\"op\":\"Total\",\"value\":286720},{\"major\":253,\"minor\":2,\"op\":\"Read\",\"value\":286720},{\"major\":253,\"minor\":2,\"op\":\"Write\"},{\"major\":253,\"minor\":2,\"op\":\"Sync\"},{\"major\":253,\"minor\":2,\"op\":\"Async\",\"value\":286720},{\"major\":253,\"minor\":2,\"op\":\"Total\",\"value\":286720},{\"major\":253,\"minor\":6,\"op\":\"Read\",\"value\":48091136},{\"major\":253,\"minor\":6,\"op\":\"Write\"},{\"major\":253,\"minor\":6,\"op\":\"Sync\"},{\"major\":253,\"minor\":6,\"op\":\"Async\",\"value\":48091136},{\"major\":253,\"minor\":6,\"op\":\"Total\",\"value\":48091136}],\"io_serviced_recursive\":[{\"major\":202,\"minor\":26368,\"op\":\"Read\",\"value\":70},{\"major\":202,\"minor\":26368,\"op\":\"Write\"},{\"major\":202,\"minor\":26368,\"op\":\"Sync\"},{\"major\":202,\"minor\":26368,\"op\":\"Async\",\"value\":70},{\"major\":202,\"minor\":26368,\"op\":\"Total\",\"value\":70},{\"major\":253,\"minor\":1,\"op\":\"Read\",\"value\":70},{\"major\":253,\"minor\":1,\"op\":\"Write\"},{\"major\":253,\"minor\":1,\"op\":\"Sync\"},{\"major\":253,\"minor\":1,\"op\":\"Async\",\"value\":70},{\"major\":253,\"minor\":1,\"op\":\"Total\",\"value\":70},{\"major\":253,\"minor\":2,\"op\":\"Read\",\"value\":70},{\"major\":253,\"minor\":2,\"op\":\"Write\"},{\"major\":253,\"minor\":2,\"op\":\"Sync\"},{\"major\":253,\"minor\":2,\"op\":\"Async\",\"value\":70},{\"major\":253,\"minor\":2,\"op\":\"Total\",\"value\":70},{\"major\":253,\"minor\":6,\"op\":\"Read\",\"value\":2748},{\"major\":253,\"minor\":6,\"op\":\"Write\"},{\"major\":253,\"minor\":6,\"op\":\"Sync\"},{\"major\":253,\"minor\":6,\"op\":\"Async\",\"value\":2748},{\"major\":253,\"minor\":6,\"op\":\"Total\",\"value\":2748}]},\"cpu_stats\":{\"cpu_usage\":{\"percpu_usage\":[45067744450,42956739916,0,0,0,0,0,0,0,0,0,0,0,0,0],\"usage_in_usermode\":30490000000,\"total_usage\":88024484366,\"usage_in_kernelmode\":1790000000},\"system_cpu_usage\":110597470000000,\"throttling_data\":{}},\"precpu_stats\":{\"cpu_usage\":{\"percpu_usage\":[45066756090,42956390106,0,0,0,0,0,0,0,0,0,0,0,0,0],\"usage_in_usermode\":30490000000,\"total_usage\":88023146196,\"usage_in_kernelmode\":1790000000},\"system_cpu_usage\":110595490000000,\"throttling_data\":{}},\"storage_stats\":{}}}",
                MediaType.APPLICATION_JSON));

		String body = this.mockMvc.perform(get("/stats"))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse().getContentAsString();

		Map json = new ObjectMapper().readValue(body, Map.class);
		assertThat(json.get("0eac38065c1468044403dc5e2624cf2196c430d5aa86a60720d0c63f3dfeb049")).isNotNull();
	}
}

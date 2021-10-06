package com.tangzhangss.commonflowable.api;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.tangzhangss.commonutils.base.SysContext;
import com.tangzhangss.commonutils.resultdata.Result;
import com.tangzhangss.commonutils.utils.BaseUtil;
import com.tangzhangss.commonutils.utils.ExceptionUtil;
import com.tangzhangss.commonutils.utils.RequestUtil;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.flowable.bpmn.constants.BpmnXMLConstants;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.impl.persistence.entity.DeploymentEntityImpl;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentQuery;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Api(tags = "流程公众接口")
@RestController
@RequestMapping("/common_api")
public class CommonApi {
    @Autowired
    ProcessEngine processEngine;
    @Autowired
    HttpServletResponse response;

    public String getTenantId(){
        return Optional.ofNullable(SysContext.getClientId()).orElse("anon");
    }

    /**
     * 获取部署的流程
     */
    @GetMapping("/get_all_process_list")
    public Result getAllProcessList(){
        RepositoryService repositoryService=processEngine.getRepositoryService();
        List<Deployment> deployments = repositoryService.createDeploymentQuery().deploymentTenantId(getTenantId()).list();
        //查询流程定义
        return Result.ok().data(JSONUtil.parseArray(deployments));
    }

    /**
     * 获取流程定义
     */
    @ApiOperation("获取流程定义")
    @GetMapping("/get_all_process_definition_list")
    public Result getAllProcessDefinitionList(){
        //创建RepositoryService实例
        RepositoryService repositoryService=processEngine.getRepositoryService();
        List<ProcessDefinition> ProcessDefinitionList = repositoryService.createProcessDefinitionQuery()
                .processDefinitionTenantId(getTenantId()).list();
        //查询流程定义
        return Result.ok().data(JSONUtil.parseArray(ProcessDefinitionList));
    }

    /**
     * 部署流程
     * 导入同样的流程会更新版本，以流程定义为主
     * flowable会根据tenantId和流程的key去区分流程的版本+1
     * bpmn20.xml
     */
    @ApiOperation(value = "部署流程",notes = "导入同样的流程会更新版本，" +
            "以流程定义为主,flowable会根据tenantId和流程的key去区分流程的版本+1," +
            "仅支持部署以.bpmn20.xml结尾的流程文件")
    @PostMapping("/deploy_process")
    public Result deployProcess(@RequestParam(name = "file") MultipartFile file) throws IOException {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        String fileOriginalFilename = file.getOriginalFilename();
        if(!fileOriginalFilename.endsWith(".bpmn20.xml")){
            ExceptionUtil.throwException("仅支持部署以.bpmn20.xml结尾的流程文件");
        }
        String filename = fileOriginalFilename.substring(0,fileOriginalFilename.indexOf(".bpmn20.xml"));

        Deployment deployment;
        try(InputStream inputStream =  file.getInputStream()){
            //resourceName 必须以.bpmn20.xml结尾不然不会向ACT_RE_PROCDEF插入数据
            deployment = repositoryService.createDeployment().name(filename).tenantId(getTenantId()).addInputStream(file.getOriginalFilename(),inputStream).deploy();
        }

        return Result.ok().data(deployment);
    }

    @ApiOperation("获取流程图片")
    @ApiImplicitParam(name = "id",value = "流程定义[processDefinition]的id")
    @GetMapping("/get_process_diagram/{id}")
    public void getProcessDiagram(@PathVariable("id") String id) throws IOException {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionTenantId(getTenantId())
                .processDefinitionId(id)
                .singleResult();
        if(processDefinition==null)ExceptionUtil.throwException("参数错误:id不存在!");


        response.setContentType("image/png");

        try(InputStream resourceAsStream = repositoryService.getResourceAsStream(
                processDefinition.getDeploymentId(), processDefinition.getDiagramResourceName());
            OutputStream outputStream = response.getOutputStream()){
            int len=0;
            byte[] b = new byte[2048];
            while((len=resourceAsStream.read(b))!=-1){
                outputStream.write(b,0,len);
            }
        }
    }

    /**
     * 获取流程图(运行时的)
     * 高亮最终
     */
    @GetMapping(value = "/get_process_diagram_activity/{processInstanceId}")
    public Result getProcessDiagramActivity(@PathVariable("processInstanceId")String processInstanceId) throws IOException {
        ProcessInstance pi = processEngine.getRuntimeService()
                .createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        String processDefinitionId = null;
        if(pi!=null){
            processDefinitionId = pi.getProcessDefinitionId();
        }
        //获取历史任务节点
        List<HistoricActivityInstance> historyActivityInstanceList = getHistoryActivityInstance(processInstanceId);
        List<String> activityIds = new ArrayList<>();
        List<String> flows = new ArrayList<>();
        for (HistoricActivityInstance hi : historyActivityInstanceList) {
            if (processDefinitionId == null) processDefinitionId = hi.getProcessDefinitionId();
            String activityType = hi.getActivityType();
            switch (activityType) {
                case BpmnXMLConstants.ELEMENT_SEQUENCE_FLOW:
                case BpmnXMLConstants.ELEMENT_GATEWAY_EXCLUSIVE: {
                    flows.add(hi.getActivityId());
                    break;
                }
                case BpmnXMLConstants.ELEMENT_EVENT_END:{
                    activityIds.add(hi.getActivityId());
                    break;
                }
//                case "userTask":
//                case "startEvent":
//                case "serviceTask":

            }
        }
        //得到正在执行的activity的id
        List<Task> taskList = processEngine.getTaskService().createTaskQuery().processInstanceId(processInstanceId).list();
        for (Task task : taskList) {
            activityIds.add(task.getTaskDefinitionKey());
        }
        //获取流程图
        BpmnModel bpmnModel = processEngine.getRepositoryService().getBpmnModel(processDefinitionId);
        ProcessEngineConfiguration engConf=processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator processDiagramGenerator = engConf.getProcessDiagramGenerator();

        response.setContentType("image/png");

        try(
            InputStream inputStream = processDiagramGenerator.generateDiagram(bpmnModel, "png", activityIds, flows, "宋体", "宋体", "宋体", null, 1.0,false);
            OutputStream outputStream = response.getOutputStream()){
            int len=0;
            byte[] b = new byte[2048];
            while((len=inputStream.read(b))!=-1){
                outputStream.write(b,0,len);
            }
        }
        return Result.ok();
    }
    /**
     * 移除流程
     * 强制删除所有流程相关的信息
     */
    @ApiOperation(value = "移除流程",notes = "强制删除所有流程相关的信息")
    @ApiImplicitParam(name="id",value="流程id",required=true)
    @DeleteMapping("/delete_process/{id}")
    public Result deleteProcess(@PathVariable("id")String id){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.deleteDeployment(id,true);
        return Result.ok();
    }

    /**
     * 移除所有的流程
     * 强制删除所有流程相关的信息
     */
    @ApiOperation(value = "移除所有的流程",notes = "强制删除所有流程相关的信息")
    @DeleteMapping("/delete_all_process")
    public Result deleteAllProcess(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        List<Deployment> deployments = repositoryService.createDeploymentQuery().deploymentTenantId(getTenantId()).list();
        deployments.forEach(deployment ->
                repositoryService.deleteDeployment(deployment.getId(),true)
        );
        return Result.ok();
    }

    /**
     * 启动流程
     * 返回流程实例的id
     */
    @ApiOperation(value = "启动流程")
    @ApiImplicitParam(name = "variables",paramType = "body",dataType="json",value = "开始流程的启动参数,如:\n" +
            "{\n" +
            "    \"employee\":\"tangzhangss\",\n" +
            "    \"nrOfHolidays\":\"How many holidays do you want to request?\",\n" +
            "    \"description\":\"Why do you need them?\"\n" +
            "}")
    @PutMapping("/start_process/{id}")
    public Result startProcess(@PathVariable("id")String id, @RequestBody HashMap<String,Object> variables){
        ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceById(id, variables);
        //返回流程实例
        return Result.ok().data(processInstance.getId());
    }

    @ApiOperation(value = "查询任务")
    @GetMapping("/find_task")
    public Result findTask(@RequestParam(required = false)String assignee,
                           @RequestParam(required = false)String id,
                           @RequestParam(required = false) String candidateGroup){
        TaskService taskService = processEngine.getTaskService();
        TaskQuery taskQuery = taskService.createTaskQuery();
        if (StringUtils.isNotBlank(assignee)){
            taskQuery.taskAssignee(assignee);
        }
        if(StringUtils.isNotBlank(candidateGroup)){
            taskQuery.taskCandidateGroup(candidateGroup);
        }
        if(StringUtils.isNotBlank(id)){
            taskQuery.taskId(id);
        }
        List<Task> list = taskQuery.taskTenantId(getTenantId()).list();

        return Result.ok().data(JSONUtil.parse(list));
    }

    @ApiOperation(value = "完成任务")
    @PutMapping("/complete_task/{id}")
    public void completeTask(@PathVariable("id")String id, @RequestBody HashMap<String,Object> variables){
        TaskService taskService = processEngine.getTaskService();
        taskService.complete(id,variables);
    }


    /**
     * 获取某个用户流程历史信息
     * @param assignee 任务分配用户
     */
    @ApiOperation("获取某个用户流程历史信息")
    @GetMapping("/get_task_history/{assignee}")
    public Result getTaskHistory(@PathVariable("assignee")String assignee){
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().taskTenantId(getTenantId()).taskAssignee(assignee).list();
        return Result.ok().data(list);
    }


    /**
     * 获取任务历史 by 流程实例id
     */
    private List<HistoricActivityInstance> getHistoryActivityInstance(String processInstanceId){
        return processEngine.getHistoryService().createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).finished().list();
    }
}

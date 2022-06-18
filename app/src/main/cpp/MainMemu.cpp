//
// Created by admin on 2022/6/14.
//
#include "ImguiEGL.h"
static bool isLoginButton=false;
static bool StyleState= false;
static bool MainMenu= true;
static bool v1= true,v2= true;//测试窗口1和2
static bool b1= false;
char ca[1024];
void ImguiEGL::MainView(){
    ImGui::SetNextWindowBgAlpha(0.7);
    //input->ItemActive  = ImGui::IsAnyItemActive();
    //input->ItemHovered = ImGui::IsAnyItemHovered();
    //input->ItemFocused = ImGui::IsAnyItemFocused();
    style->WindowTitleAlign = ImVec2(0.5, 0.5);//标题居中
    //style->WindowMinSize = ImVec2(900, 800);
   // FrameHeight = ImGui::GetFrameHeight();
    if (input->Scrollio&&!input->Activeio) {
        input->funScroll(g->WheelingWindow ? g->WheelingWindow : g->HoveredWindow);
    }

        ImGui::Begin("imgui模板");//第一个窗口最好不要用if包起来

        input->g_window  = g_window = ImGui::GetCurrentWindow();
        ImGui::SetWindowSize({900, 800}, ImGuiCond_FirstUseEver);
        ImGui::SetWindowPos({0, 200}, ImGuiCond_FirstUseEver);

        ImGui::Text("登录....");

       // ImGui::Text("焦点id:%d",ImGui::GetFocusID().);

        if(ImGui::BeginTabBar("tipss")){
            if(ImGui::BeginTabItem("测试1")){
                if (!b1){
                    ImGui::InputTextWithHint("##输入密码", "输入密码", ca, IM_ARRAYSIZE(ca), ImGuiInputTextFlags_CallbackAlways, ImguiAndroidInput::inputCallback);
                    if(ImGui::Button("登录")){
                        // int a=ca-'0';
                        string pass="pass";
                        string s=ca;
                        if (s==pass){
                            isLoginButton= true;
                            login= true;
                            //b1= true;
                        } else isLoginButton= true;

                    }
                    // LOGE("当前:%s",ca);
                    if (isLoginButton){
                        Menu1();
                    }
                    //ImGui::SameLine();

                    ImGui::SameLine();
                    if (ImGui::Button("OK")) {
                        //string  s="test";
                        //input->toast(s);
                        login = true;
                        b1=true;
                        //exit(233);
                    }
                    ImGui::SameLine();
                    if (ImGui::Button("退出")) {
                        login = true;
                        exit(233);
                    }
                }
                if(ImGui::Button("恢复登录状态")) {
                    login= false;
                    isLoginButton= false;
                    b1=false;

                }
                ImGui::Checkbox("测试窗口1",&v1);
                ImGui::Checkbox("测试窗口2",&v2);


              ImGui::EndTabItem();
            }

            if(ImGui::BeginTabItem("设置")){

                if(ImGui::Checkbox("主题编辑",&StyleState)) {
                    //StyleState=!StyleState;

                }

                ImGui::SameLine();
               static int tp=0;
                if(ImGui::Button("保存")) {
                    string SaveFile = this->SaveDir;
                    SaveFile += "/Style.dat";
                    tp=MyFile::SaveInFile(&ImGui::GetStyle(),SaveFile.c_str());
                    isStyle= true;
                    LOGE("状态:%d",tp);
                }
                ImGui::SameLine();
                if(ImGui::Button("读取")) {
                    string LoadFile = this->SaveDir;
                    LoadFile += "/Style.dat";
                    ImGuiStyle s;
                    tp= MyFile::ReadFile(&s,LoadFile.c_str());
                    isStyle= true;
                    LOGE("状态A:%d",tp);

                }

                if (isStyle){//主题是否保存/读取成功
                    Dialog(tp);
                }

                if (StyleState){
                    ShowStyleEditor(&ImGui::GetStyle());
                }
                ImGui::EndTabItem();
            }

            ImGui::EndTabBar();

        }
        ImGui::End();






            if(v1){
            ImGui::Begin("imgui模板1", &v1 );
            ImGui::SetWindowSize({700, 550}, ImGuiCond_FirstUseEver);
            ImGui::SetWindowPos({20, 400}, ImGuiCond_FirstUseEver);
            //input->winio = ImGui::IsWindowCollapsed();
            //input->winWidth = ImGui::GetWindowWidth();
            //input->winHeight = ImGui::GetWindowHeight();
            input->g_window = g_window = ImGui::GetCurrentWindow();
            ImGui::Text("测试....");
            ImGui::End();
            }


            if(v2){
            ImGui::Begin("imgui模板2", &v2 );
            ImGui::SetWindowSize({600, 500}, ImGuiCond_FirstUseEver);
            ImGui::SetWindowPos({10, 600}, ImGuiCond_FirstUseEver);
            //input->winio = ImGui::IsWindowCollapsed();
            //input->winWidth = ImGui::GetWindowWidth();
            //input->winHeight = ImGui::GetWindowHeight();
            input->g_window = g_window = ImGui::GetCurrentWindow();

            ImGui::Text("测试....");
            ImGui::End();
            }



}
void ImguiEGL::Menu1(){
    ImGui::SetNextWindowSize({430, 260});
    ImGui::SetNextWindowPos(ImVec2((g_window->Pos.x + g_window->Size.x * 0.5f) - 430 * 0.5f, (g_window->Pos.y + g_window->Size.y * 0.5f) - 260 * 0.5f));
    ImGui::OpenPopup("提示");
    if (ImGui::BeginPopupModal("提示", nullptr, ImGuiWindowFlags_AlwaysAutoResize)) {
//            LOGE("开启弹窗");
        if (login) {
            ImGui::Text("登录成功！");
            ImGui::Text("到期时间:999");
            if (ImGui::Button("关闭")) {
                LOGE("关闭弹窗");
                isLoginButton= false;
                b1= true;
                ImGui::CloseCurrentPopup();
            }
        } else {
            ImGui::Text("登录失败:hh");
            //ImGui::Text("到期时间:%s", jjydata->dqsj.c_str());
            if (ImGui::Button("关闭")) {
                LOGE("关闭弹窗");
                isLoginButton= false;
                ImGui::CloseCurrentPopup();
            }
        }
        ImGui::EndPopup();
    }

}

void ImguiEGL::Dialog(int type){
    ImGui::SetNextWindowSize({430, 260});
    ImGui::SetNextWindowPos(ImVec2((g_window->Pos.x + g_window->Size.x * 0.5f) - 430 * 0.5f, (g_window->Pos.y + g_window->Size.y * 0.5f) - 260 * 0.5f));
    ImGui::OpenPopup("提示");
    if (ImGui::BeginPopupModal("提示", nullptr, ImGuiWindowFlags_AlwaysAutoResize)) {
//            LOGE("开启弹窗");
        if (type==1) {
            ImGui::Text("成功！");
            if (ImGui::Button("关闭")) {
                LOGE("关闭弹窗");
                isStyle= false;
                ImGui::CloseCurrentPopup();
            }
        } else {
            ImGui::Text("失败:hh");
            //ImGui::Text("到期时间:%s", jjydata->dqsj.c_str());
            if (ImGui::Button("关闭")) {
                LOGE("关闭弹窗");
                isStyle= false;
                ImGui::CloseCurrentPopup();
            }
        }
        ImGui::EndPopup();
    }

}



//主题编辑器
#define IM_MAX(A, B)            (((A) >= (B)) ? (A) : (B))
void ImguiEGL::ShowStyleEditor(ImGuiStyle* ref)
{
    ImGui::SetNextWindowSize({430, 260});
    ImGui::SetNextWindowPos(ImVec2((g_window->Pos.x + g_window->Size.x * 0.5f) - 430 * 0.5f, (g_window->Pos.y + g_window->Size.y * 0.5f) - 260 * 0.5f));
    //IMGUI_DEMO_MARKER("Tools/Style Editor");
    // You can pass in a reference ImGuiStyle structure to compare to, revert to and save to
    // (without a reference style pointer, we will use one compared locally as a reference)
    ImGuiStyle& style = ImGui::GetStyle();
    static ImGuiStyle ref_saved_style;

    // Default to using internal storage as reference
    static bool init = true;
    if (init && ref == NULL)
        ref_saved_style = style;
    init = false;
    if (ref == NULL)
        ref = &ref_saved_style;

    ImGui::PushItemWidth(ImGui::GetWindowWidth() * 0.50f);

    if (ImGui::ShowStyleSelector("主题##Selector"))
        ref_saved_style = style;

    // Simplified Settings (expose floating-pointer border sizes as boolean representing 0.0f or 1.0f)
    if (ImGui::SliderFloat("圆角", &style.FrameRounding, 0.0f, 20.0f, "%.0f"))
        style.GrabRounding = style.FrameRounding; // Make GrabRounding always the same value as FrameRounding
    { bool border = (style.WindowBorderSize > 0.0f); if (ImGui::Checkbox("窗口边框", &border)) { style.WindowBorderSize = border ? 1.0f : 0.0f; } }
    ImGui::SameLine();
    { bool border = (style.FrameBorderSize > 0.0f);  if (ImGui::Checkbox("框架边框", &border)) { style.FrameBorderSize = border ? 1.0f : 0.0f; } }
    ImGui::SameLine();
    { bool border = (style.PopupBorderSize > 0.0f);  if (ImGui::Checkbox("弹出框", &border)) { style.PopupBorderSize = border ? 1.0f : 0.0f; } }


    ImGui::SameLine();
    // HelpMarker("在本地非持久存储中保存/恢复。默认颜色定义不受影响。\"\ \"使用下面的“导出”将它们保存在某个地方。");

    ImGui::Separator();

    if (ImGui::BeginTabBar("##tabs", ImGuiTabBarFlags_None))
    {
        if (ImGui::BeginTabItem("细节调整"))
        {
            ImGui::Text("主窗口");
            ImGui::SliderFloat2("窗口填充", (float*)&style.WindowPadding, 0.0f, 20.0f, "%.0f");
            ImGui::SliderFloat2("圆角填充", (float*)&style.FramePadding, 0.0f, 20.0f, "%.0f");
            ImGui::SliderFloat2("单元格填充", (float*)&style.CellPadding, 0.0f, 20.0f, "%.0f");
            ImGui::SliderFloat2("项目间距", (float*)&style.ItemSpacing, 0.0f, 20.0f, "%.0f");
            ImGui::SliderFloat2("项目内部间距", (float*)&style.ItemInnerSpacing, 0.0f, 20.0f, "%.0f");
            ImGui::SliderFloat2("触摸额外填充", (float*)&style.TouchExtraPadding, 0.0f, 10.0f, "%.0f");
            ImGui::SliderFloat("缩进间距", &style.IndentSpacing, 0.0f, 30.0f, "%.0f");
            ImGui::SliderFloat("滚动条大小", &style.ScrollbarSize, 1.0f, 20.0f, "%.0f");
            ImGui::SliderFloat("抓取器最小尺寸", &style.GrabMinSize, 1.0f, 20.0f, "%.0f");
            ImGui::Text("Borders");
            ImGui::SliderFloat("窗口边框大小", &style.WindowBorderSize, 0.0f, 1.0f, "%.0f");
            ImGui::SliderFloat("子边框尺寸", &style.ChildBorderSize, 0.0f, 1.0f, "%.0f");
            ImGui::SliderFloat("弹出边框大小", &style.PopupBorderSize, 0.0f, 1.0f, "%.0f");
            ImGui::SliderFloat("边框尺寸", &style.FrameBorderSize, 0.0f, 1.0f, "%.0f");
            ImGui::SliderFloat("选项卡边框大小", &style.TabBorderSize, 0.0f, 1.0f, "%.0f");
            ImGui::Text("细节调整");
            ImGui::SliderFloat("窗口圆角", &style.WindowRounding, 0.0f, 20.0f, "%.0f");
            ImGui::SliderFloat("ChildRounding", &style.ChildRounding, 0.0f, 12.0f, "%.0f");
            ImGui::SliderFloat("控件圆角调整", &style.FrameRounding, 0.0f, 30.0f, "%.0f");
            ImGui::SliderFloat("弹出调整", &style.PopupRounding, 0.0f, 12.0f, "%.0f");
            ImGui::SliderFloat("滚动条调整", &style.ScrollbarRounding, 0.0f, 12.0f, "%.0f");
            ImGui::SliderFloat("抓取调整", &style.GrabRounding, 0.0f, 12.0f, "%.0f");
            ImGui::SliderFloat("日志滑块死区", &style.LogSliderDeadzone, 0.0f, 12.0f, "%.0f");
            ImGui::SliderFloat("制表符调整", &style.TabRounding, 0.0f, 12.0f, "%.0f");
            ImGui::Text("对齐方式");
            ImGui::SliderFloat2("窗口标题对齐", (float*)&style.WindowTitleAlign, 0.0f, 1.0f, "%.2f");
            int window_menu_button_position = style.WindowMenuButtonPosition + 1;
            if (ImGui::Combo("窗口菜单按钮位置", (int*)&window_menu_button_position, "None\0Left\0Right\0"))
                style.WindowMenuButtonPosition = window_menu_button_position - 1;
            ImGui::Combo("颜色按钮位置", (int*)&style.ColorButtonPosition, "Left\0Right\0");
            ImGui::SliderFloat2("按钮文本对齐", (float*)&style.ButtonTextAlign, 0.0f, 1.0f, "%.2f");
            // ImGui::SameLine(); HelpMarker("当按钮大于其文本内容时应用对齐。");
            ImGui::SliderFloat2("可选文本对齐", (float*)&style.SelectableTextAlign, 0.0f, 1.0f, "%.2f");
            // ImGui::SameLine(); HelpMarker("当可选项大于其文本内容时，将应用对齐方式.");
            ImGui::Text("安全区域填充");
            ImGui::SameLine();// HelpMarker("如果看不到屏幕边缘，请进行调整（例如，在未配置缩放的电视上）.");
            ImGui::SliderFloat2("显示安全区域填充", (float*)&style.DisplaySafeAreaPadding, 0.0f, 30.0f, "%.0f");
            ImGui::EndTabItem();
        }

        if (ImGui::BeginTabItem("主题颜色"))
        {
            static int output_dest = 0;
            static bool output_only_modified = true;

            //ImGui::SameLine(); ImGui::Checkbox("仅修改后的颜色", &output_only_modified);

            static ImGuiTextFilter filter;
            filter.Draw("过滤颜色", ImGui::GetFontSize() * 16);

            static ImGuiColorEditFlags alpha_flags = 0;
            if (ImGui::RadioButton("不透明", alpha_flags == ImGuiColorEditFlags_None)) { alpha_flags = ImGuiColorEditFlags_None; } ImGui::SameLine();
            if (ImGui::RadioButton("透明", alpha_flags == ImGuiColorEditFlags_AlphaPreview)) { alpha_flags = ImGuiColorEditFlags_AlphaPreview; } ImGui::SameLine();
            if (ImGui::RadioButton("透明加粗", alpha_flags == ImGuiColorEditFlags_AlphaPreviewHalf)) { alpha_flags = ImGuiColorEditFlags_AlphaPreviewHalf; } //ImGui::SameLine();
            ImGui::Text("按住窗口右下角调整窗口大小");

            ImGui::BeginChild("##colors", ImVec2(0, 0), true, ImGuiWindowFlags_AlwaysVerticalScrollbar | ImGuiWindowFlags_AlwaysHorizontalScrollbar | ImGuiWindowFlags_NavFlattened);
            ImGui::PushItemWidth(-160);
            for (int i = 0; i < ImGuiCol_COUNT; i++)
            {
                const char* name = ImGui::GetStyleColorName(i);
                if (!filter.PassFilter(name))
                    continue;
                ImGui::PushID(i);
                ImGui::ColorEdit4("##color", (float*)&style.Colors[i], ImGuiColorEditFlags_AlphaBar | alpha_flags);
                if (memcmp(&style.Colors[i], &ref->Colors[i], sizeof(ImVec4)) != 0)
                {
                    // Tips: in a real user application, you may want to merge and use an icon font into the main font,
                    // so instead of "Save"/"Revert" you'd use icons!
                    // Read the FAQ and docs/FONTS.md about using icon fonts. It's really easy and super convenient!
                    ImGui::SameLine(0.0f, style.ItemInnerSpacing.x); if (ImGui::Button("保存")) { ref->Colors[i] = style.Colors[i]; }
                    ImGui::SameLine(0.0f, style.ItemInnerSpacing.x); if (ImGui::Button("复原")) { style.Colors[i] = ref->Colors[i]; }
                }
                ImGui::SameLine(0.0f, style.ItemInnerSpacing.x);
                ImGui::TextUnformatted(name);
                ImGui::PopID();
            }
            ImGui::PopItemWidth();
            ImGui::EndChild();

            ImGui::EndTabItem();
        }



        if (ImGui::BeginTabItem("渲染"))
        {
            ImGui::Checkbox("抗锯齿线", &style.AntiAliasedLines);
            ImGui::SameLine();
            //HelpMarker("禁用抗锯齿线时，您可能还希望禁用样式中的边框");

            ImGui::Checkbox("抗锯齿线使用纹理", &style.AntiAliasedLinesUseTex);
            ImGui::SameLine();
            // HelpMarker("使用纹理数据更快的线条。 要求后端使用双线性过滤（不是点/最近过滤）进行渲染。");

            ImGui::Checkbox("抗锯齿填充", &style.AntiAliasedFill);
            ImGui::PushItemWidth(ImGui::GetFontSize() * 8);
            ImGui::DragFloat("曲线镶嵌公差", &style.CurveTessellationTol, 0.02f, 0.10f, 10.0f, "%.2f");
            if (style.CurveTessellationTol < 0.10f) style.CurveTessellationTol = 0.10f;

            // When editing the "Circle Segment Max Error" value, draw a preview of its effect on auto-tessellated circles.
            ImGui::DragFloat("圆形镶嵌最大误差", &style.CircleTessellationMaxError, 0.005f, 0.10f, 5.0f, "%.2f", ImGuiSliderFlags_AlwaysClamp);
            if (ImGui::IsItemActive())
            {
                ImGui::SetNextWindowPos(ImGui::GetCursorScreenPos());
                ImGui::BeginTooltip();
                ImGui::TextUnformatted("(R = radius, N = number of segments)");
                ImGui::Spacing();
                ImDrawList* draw_list = ImGui::GetWindowDrawList();
                const float min_widget_width = ImGui::CalcTextSize("N: MMM\nR: MMM").x;
                for (int n = 0; n < 8; n++)
                {
                    const float RAD_MIN = 5.0f;
                    const float RAD_MAX = 70.0f;
                    const float rad = RAD_MIN + (RAD_MAX - RAD_MIN) * (float)n / (8.0f - 1.0f);

                    ImGui::BeginGroup();

                    ImGui::Text("R: %.f\nN: %d", rad, draw_list->_CalcCircleAutoSegmentCount(rad));

                    const float canvas_width = IM_MAX(min_widget_width, rad * 2.0f);
                    const float offset_x = floorf(canvas_width * 0.5f);
                    const float offset_y = floorf(RAD_MAX);

                    const ImVec2 p1 = ImGui::GetCursorScreenPos();
                    draw_list->AddCircle(ImVec2(p1.x + offset_x, p1.y + offset_y), rad, ImGui::GetColorU32(ImGuiCol_Text));
                    ImGui::Dummy(ImVec2(canvas_width, RAD_MAX * 2));

                    /*
                    const ImVec2 p2 = ImGui::GetCursorScreenPos();
                    draw_list->AddCircleFilled(ImVec2(p2.x + offset_x, p2.y + offset_y), rad, ImGui::GetColorU32(ImGuiCol_Text));
                    ImGui::Dummy(ImVec2(canvas_width, RAD_MAX * 2));
                    */

                    ImGui::EndGroup();
                    ImGui::SameLine();
                }
                ImGui::EndTooltip();
            }
            ImGui::SameLine();
            // HelpMarker("当使用“num_segments == 0”绘制圆形图元时，将自动计算镶嵌。");

            ImGui::DragFloat("全部透明", &style.Alpha, 0.005f, 0.20f, 1.0f, "%.2f"); // Not exposing zero here so user doesn't "lose" the UI (zero alpha clips all widgets). But application code could have a toggle to switch between zero and non-zero.
            ImGui::DragFloat("禁用透明", &style.DisabledAlpha, 0.005f, 0.0f, 1.0f, "%.2f"); ImGui::SameLine(); //HelpMarker("Additional alpha multiplier for disabled items (multiply over current value of Alpha).");
            ImGui::PopItemWidth();

            ImGui::EndTabItem();
        }

        ImGui::EndTabBar();
    }

    ImGui::PopItemWidth();

}

char *trim(char *str) {
    //这里是指针在跑
    char *point = str;//组成新的指针
    while (*point != '\0')//让指针往后跑，跑到最后一个空字符的位置停下来
    {
        point++;//结束时指针跑到最后一元素哪里了
    }
    point--;//后退一位，以除去最后一位的那个空字符
    while (point >= str && *point == ' ')//往回跑以除去尾部的空格字符，没有空格了就停下来
    {
        *point = '\0';
        point--;
    }//此时除去了尾部的空格字符
    point = str;//重新定义point回到第一位从头部开始除去空格字符
    while (*point == ' ') {
        point++;//结束时的位置即为新的数组首
    }
    return point;
}
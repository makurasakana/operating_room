describe("测试", function () {//describe就是对这次测试的整体描述，如：加法测试
    it("成功", function () {//it是对个测试的描述，如：3+5应该等于8
      expect(add(3, 5)).toEqual(8);//期待add（3,5）的结果等于8
    });
 });